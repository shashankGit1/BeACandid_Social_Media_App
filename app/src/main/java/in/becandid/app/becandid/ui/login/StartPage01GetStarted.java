package in.becandid.app.becandid.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;

public class StartPage01GetStarted extends BaseActivity {
    private Button get_started_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page01);
        get_started_button = (Button) findViewById(R.id.get_started_button);

        get_started_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* final code for the logic. */
                Intent intent = new Intent(StartPage01GetStarted.this, StartPage02FacebookLogin.class);
                startActivity(intent);

            //    existingUser();
            }
        });
    }

    @Override
    protected void setUp() {

    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, StartPage01GetStarted.class);
        return intent;
    }

    private void existingUser(){

        try{
            UserData();
            application.getAuth().setAuthToken("token");
            application.getAuth().getUser().setLoggedIn(true);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            Intent intent = new Intent(StartPage01GetStarted.this, DiscoverActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private synchronized void UserData() {
        MySharedPreferences.registerUserId(preferences, "2");
       // MySharedPreferences.registerUserToken(preferences, "1");
       // MySharedPreferences.registerUsername(preferences, "paras");
    //    MySharedPreferences.registerImageUrl(preferences, "");
    //    MySharedPreferences.registerSocialID(preferences, "");
    }
}
