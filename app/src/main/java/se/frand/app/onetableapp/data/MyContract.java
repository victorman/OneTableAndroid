package se.frand.app.onetableapp.data;

import android.provider.BaseColumns;

/**
 * Created by victorfrandsen on 9/14/15.
 */
public final class MyContract {
    public MyContract() {};

    public static abstract class DateEntry implements BaseColumns {
        public static final String TABLE_NAME = "datetime";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_CREATED = "datecreated";
        public static final String COLUMN_NAME_NOTE = "note";
    }

}
