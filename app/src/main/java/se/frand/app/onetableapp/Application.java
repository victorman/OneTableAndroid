package se.frand.app.onetableapp;

import com.parse.Parse;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        Parse.initialize(this,
                getString(R.string.parse_app_id),
                getString(R.string.parse_client_key));
    }


}