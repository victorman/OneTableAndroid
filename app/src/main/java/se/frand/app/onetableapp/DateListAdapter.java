package se.frand.app.onetableapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import se.frand.app.onetableapp.data.MyContract;
import se.frand.app.onetableapp.data.MyDBHelper;

/**
 * Created by victorfrandsen on 9/15/15.
 */
public class DateListAdapter extends CursorAdapter {

    public static class ViewHolder {
        public final TextView dateView;
        public final TextView noteView;
        public final Button deleteButton;

        public ViewHolder(View view) {
            dateView = (TextView) view.findViewById(R.id.item_date);
            noteView = (TextView) view.findViewById(R.id.item_note);
            deleteButton = (Button) view.findViewById(R.id.item_delete_button);
        }
    }

    public DateListAdapter(Context context, Cursor c) {
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_selected_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.dateView.setText(MainActivity.getDate(
                cursor.getLong(MainActivity.COL_DATETIME_CREATED),
                "h:mm a M/d"
        ));

        viewHolder.deleteButton.setText(R.string.delete);
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = context.openOrCreateDatabase(
                        MyDBHelper.DATABASE_NAME,
                        Context.MODE_PRIVATE,
                        null
                );
                String where = MyContract.DateEntry.COLUMN_NAME_ID + "=?";
                String[] args = {""+cursor.getInt(MainActivity.COL_DATETIME_ID)};
                db.delete(MyContract.DateEntry.TABLE_NAME, where, args);
                swapCursor(MainActivity.getLogTimes(db));
            }
        });

        viewHolder.noteView.setText(cursor.getString(MainActivity.COL_DATETIME_NOTE));
    }
}
