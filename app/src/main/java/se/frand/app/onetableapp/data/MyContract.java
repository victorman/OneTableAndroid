package se.frand.app.onetableapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by victorfrandsen on 9/14/15.
 */
public final class MyContract {
    public static final String CONTENT_AUTHORITY = "se.frand.app.onetableapp";
    public static final String PATH_NOTES = "notes";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public MyContract() {};

    public static abstract class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "datetime";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_CREATED = "datecreated";
        public static final String COLUMN_NAME_NOTE = "note";


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTES;
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NOTES).build();

        public static Uri buildNoteUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
