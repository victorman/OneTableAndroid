package se.frand.app.onetableapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import se.frand.app.onetableapp.data.MyContract;

/**
 * Created by victorfrandsen on 9/25/15.
 */
public class DateListAdapter extends CursorAdapter {


    private static final String LOG_TAG = DateListAdapter.class.getSimpleName() ;

    public static class ViewHolder {
        public final TextView dateView;
        public final TextView noteView;
        public final Button deleteButton;
        public final TextView invisibleView;

        public ViewHolder(View view) {
            dateView = (TextView) view.findViewById(R.id.item_date);
            noteView = (TextView) view.findViewById(R.id.item_note);
            deleteButton = (Button) view.findViewById(R.id.item_delete_button);
            invisibleView = (TextView) view.findViewById(R.id.note_id);
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
    public void bindView(final View view, final Context context, final Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.dateView.setText(MainActivity.getDate(
                cursor.getLong(MainActivity.COL_DATETIME_CREATED),
                "h:mm a M/d"
        ));

        viewHolder.deleteButton.setText(R.string.delete);
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String where = MyContract.NoteEntry.COLUMN_NAME_ID + "=?";

                String[] args = new String[] { ""+((TextView)view.findViewById(R.id.note_id)).getText()};

                context.getContentResolver().delete(MyContract.NoteEntry.CONTENT_URI, where, args);
                swapCursor(MainActivity.getLogTimes(context));
            }
        });

        viewHolder.noteView.setText(cursor.getString(MainActivity.COL_DATETIME_NOTE));
        viewHolder.invisibleView.setText(cursor.getString(MainActivity.COL_DATETIME_ID));
    }
}
