package se.frand.app.onetableapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by victorfrandsen on 9/15/15.
 */
public class DateListAdapter extends ParseQueryAdapter<ParseObject> {
    private final String LOG_TAG = DateListAdapter.class.getSimpleName();

    public static final String COLUMN_NAME_ID = "objectId";
    public static final String COLUMN_NAME_CREATED = "createdAt";
    public static final String COLUMN_NAME_NOTE = "note";

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

    public DateListAdapter(Context context) {
        super(context,new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {
                ParseQuery query = new ParseQuery("Note");
                query.whereExists("note");
                query.orderByDescending("note");
                return query;
            }
        });
    }

    @Override
    public View getItemView(final ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.list_item_selected_layout, null);
        }
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.dateView.setText(MainActivity.getDate(
                object.getLong(COLUMN_NAME_CREATED),
                "h:mm a M/d"
        ));

        viewHolder.deleteButton.setText(R.string.delete);
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDialog(object);
            }
        });

        viewHolder.noteView.setText(object.getString(COLUMN_NAME_NOTE));
        return v;
    }


    private void newDialog(final ParseObject object) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete this note?");

        // Set up the buttons
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete
                object.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        loadObjects();
                    }
                });
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
