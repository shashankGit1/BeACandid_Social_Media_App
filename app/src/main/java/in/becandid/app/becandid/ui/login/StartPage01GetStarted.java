package in.becandid.app.becandid.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;

public class StartPage01GetStarted extends BaseActivity {
    private Button get_started_button;
    private CheckBox checkBox;
    private TextView terms_and_conditions;
    private boolean checkedTerms = false;
    private ProgressBar webviewProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page01);
        get_started_button = (Button) findViewById(R.id.get_started_button);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        terms_and_conditions = (TextView) findViewById(R.id.terms_and_conditions);
        webviewProgressBar = (ProgressBar) findViewById(R.id.webviewProgressBar);
        webviewProgressBar.setVisibility(View.GONE);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    checkBox.setChecked(true);
                    checkedTerms = true;

                } else {
                    checkBox.setChecked(false);
                    checkedTerms = false;

                }

            }
        });

        terms_and_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webviewProgressBar.setVisibility(View.VISIBLE);


                AlertDialog.Builder alert = new AlertDialog.Builder(StartPage01GetStarted.this);
                alert.setTitle("Our Terms and Conditions");

                WebView wv = new WebView(StartPage01GetStarted.this);
                wv.loadUrl("https://beacandid.com/terms-and-conditions/");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        webviewProgressBar.setVisibility(View.GONE);


                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        webviewProgressBar.setVisibility(View.GONE);

                    }
                });
                alert.show();
            }
        });

        get_started_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* final code for the logic. */
                if (checkedTerms){
                    Intent intent = new Intent(StartPage01GetStarted.this, StartPage02FacebookLogin.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(StartPage01GetStarted.this, "Please agree to the Terms and Conditions", Toast.LENGTH_SHORT).show();
                }


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
