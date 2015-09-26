package se.frand.app.onetableapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import se.frand.app.onetableapp.data.MyContract;
import se.frand.app.onetableapp.data.MyDBHelper;


public class MainActivity extends Activity {

    static final int COL_DATETIME_ID = 0;
    static final int COL_DATETIME_CREATED = 1;
    static final int COL_DATETIME_NOTE = 2;

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private MyDBHelper mDbHelper;

    DateListAdapter mAdapter;
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDbHelper = new MyDBHelper(this);

        mAdapter = new DateListAdapter(this, getLogTimes(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (ListView) findViewById(R.id.dates_list_view);
        mList.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            newDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void newDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Note:");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Uri newUri = logDate(input.getText().toString());
                //redraw the list now.
                if(newUri != null) {
                    mAdapter.swapCursor(getLogTimes(getApplicationContext()));
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private Uri logDate(String note) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MyContract.NoteEntry.COLUMN_NAME_CREATED, System.currentTimeMillis());
        values.put(MyContract.NoteEntry.COLUMN_NAME_NOTE, note);

        Uri newUri = this.getContentResolver().insert(
                MyContract.NoteEntry.CONTENT_URI,
                values);

        //db.close();
        return newUri;
    }

    protected static Cursor getLogTimes(Context context){
        String[] projection = {
                MyContract.NoteEntry._ID,
                MyContract.NoteEntry.COLUMN_NAME_CREATED,
                MyContract.NoteEntry.COLUMN_NAME_NOTE
        };
        String sortOrder = MyContract.NoteEntry.COLUMN_NAME_CREATED + " DESC";

        Cursor c = context.getContentResolver().query(
                MyContract.NoteEntry.CONTENT_URI,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                sortOrder                               // The sort order
        );
        return c;
    }


    /*
     *  from Uttam http://stackoverflow.com/a/7954038
     */
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
