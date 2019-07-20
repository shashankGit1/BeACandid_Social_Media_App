package in.becandid.app.becandid.ui.login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.profile.ContactService;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.utils.ActivityUtils;

public class StartPage03Contact extends BaseActivity {

    private EditText editTextLoadCarrierNumber;
    private CountryCodePicker ccpLoadNumber;
    private Button get_phone;
    private View activity_startPage03_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page03);

        toolbar.setTitle("Contacts");

        editTextLoadCarrierNumber = (EditText) findViewById(R.id.editText_loadCarrierNumber);
        activity_startPage03_progress = (View) findViewById(R.id.activity_startPage03_progress);
        ccpLoadNumber = (CountryCodePicker) findViewById(R.id.ccp_loadFullNumber);
        get_phone = (Button) findViewById(R.id.get_phone);

        ccpLoadNumber.registerCarrierNumberEditText(editTextLoadCarrierNumber);

        get_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_startPage03_progress.setVisibility(View.VISIBLE);
                readContacts();
              //  MySharedPreferences.registerContact(preferences, "true");
                // Toast.makeText(StartPage03Contact.this, "Phone number :" + ccpLoadNumber.getFullNumberWithPlus(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void setUp() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_skip_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skip_menu:
                gotoNextPage();
                return true;
            default:
                return true;
        }
    }

    // old code below

    private void gotoNextPage(){
        Intent intent = new Intent(StartPage03Contact.this, StartPage04Gender.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*
    private void sendContact(String phoneNumber) throws Exception {
        application.getWebService()
                .registerMobile(MySharedPreferences.getUserId(preferences), phoneNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse response) {
                        Timber.e("Successfully logged in" + response.getStatus());
                        // method to get all contacts
                        readContacts();
                    }
                });
    }
    */


    private void readContacts() {
        ActivityUtils.isContactsPermission(this);
    }

    public void contactMethod(){
        startService(new Intent(this, ContactService.class));
        //  dialogBox();
        application.getAuth().getUser().setAllContacts(true);
        // method to go to next page
        gotoNextPage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == getResources().getInteger(R.integer.contacts_request)) {
                contactMethod();
            }
        }
    }
}
