package in.becandid.app.becandid.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import androidx.appcompat.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Toast;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.di.rxbus.RxBus;
import in.becandid.app.becandid.infrastructure.Account;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.base.VoicemeApplication;

import static in.becandid.app.becandid.ui.base.Constants.CONSTANT_PREF_FILE;

public class SettingsActivity extends AppCompatPreferenceActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setupActionBar();


        //setting up toolbar
        getLayoutInflater().inflate(R.layout.toolbar_setting, (ViewGroup) findViewById(android.R.id.content));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsActivityFragment()).commit();

    }

    /* private void setupActionBar() {
        ViewGroup rootView = (ViewGroup)findViewById(R.id.action_bar_root); //id from appcompat

        if (rootView != null) {
            View view = getLayoutInflater().inflate(R.layout.settings_pref, rootView, false);
            rootView.addView(view, 0);

            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    } */


    public static class SettingsActivityFragment extends PreferenceFragment
    {
        private RxBus bus;

        ListPreference agePref;
        ListPreference genderPref;
        SwitchPreference switchPref;
        SwitchPreference blockPref;
        boolean changedValue = false;
        SharedPreferences pref;
        Preference myPref;

        String key_user_gender;
        Preference updateUserTokenButton;
        String key_user_age;
        String user_id;
        boolean key_adult_filter;
        boolean key_exclude_premium_search;
        protected SharedPreferences preferences;
        protected VoicemeApplication application;

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {

            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            bus = ((VoicemeApplication)getActivity().getApplication()).getBus();
            application = ((VoicemeApplication)getActivity().getApplication());

            pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            preferences = getActivity().getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);

            user_id = MySharedPreferences.getUserId(preferences);


            key_user_gender = pref.getString("key_user_gender", "4");
            key_user_age = pref.getString("key_user_age", "6");
            key_adult_filter = pref.getBoolean("key_adult_filter", true);
            key_exclude_premium_search = pref.getBoolean("key_exclude_premium_search", false);

            bindPreferenceSummaryToValue(findPreference("key_user_gender"));
            bindPreferenceSummaryToValue(findPreference("key_user_age"));

            myPref = findPreference("key_send_feedback");
            updateUserTokenButton = findPreference("updateUserTokenButton");

            updateUserTokenButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    //code for what you want it to do

                    bus.send(new Account.saveFirebaseToken());

                    Toast.makeText(getActivity(), "Notification fix done", Toast.LENGTH_LONG).show();

                    return true;
                }
            });




            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    sendFeedback(getActivity(), user_id);
                    return true;
                }
            });

            agePref = (ListPreference) findPreference("key_user_age");
            agePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {

                    changedValue = true;
                    String ageValue = o.toString();

                    int index = agePref.findIndexOfValue(ageValue);
                    key_user_age = String.valueOf(index + 1);
                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            index >= 0
                                    ? agePref.getEntries()[index]
                                    : null);
                    return true;
                }
            });

            genderPref = (ListPreference) findPreference("key_user_gender");
            genderPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {

                    changedValue = true;
                    String genderValue = o.toString();

                    int index = genderPref.findIndexOfValue(genderValue);
                    key_user_gender = String.valueOf(index + 1);
                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            index >= 0
                                    ? genderPref.getEntries()[index]
                                    : null);
                    return true;
                }
            });

            switchPref = (SwitchPreference) findPreference("key_adult_filter");
            switchPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {

                    changedValue = true;
                    String ageValue = o.toString();
                    return true;
                }
            });

            blockPref = (SwitchPreference) findPreference("key_exclude_premium_search");
            blockPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {

                    changedValue = true;
                    String ageValue = o.toString();

                    return true;
                }
            });
        }

        private static void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }

        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();

                if (preference instanceof ListPreference) {
                    // For list preferences, look up the correct display value in
                    // the preference's 'entries' list.
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);

                    // Set the summary to reflect the new value.
                    preference.setSummary(
                            index >= 0
                                    ? listPreference.getEntries()[index]
                                    : null);

                } /* else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(R.string.summary_choose_ringtone);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("key_gallery_name")) {
                    // update the changed gallery name to summary filed
                    preference.setSummary(stringValue);
                }
            } */ else {
                    preference.setSummary(stringValue);
                }
                return true;
            }
        };

        /**
         * Email client intent to send support mail
         * Appends the necessary device information to email body
         * useful when providing support
         */
        public static void sendFeedback(Context context, String user_id) {
            String body = null;
            try {
                body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                body = "\n\n-----------------------------\nPlease don't remove this Anonymous Device information\n Device OS: Android \n Device OS version: " +
                        Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + "user ID" + "\n " + user_id +  Build.BRAND +
                        "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
            } catch (PackageManager.NameNotFoundException e) {
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ojaswi.sharma@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Query from android app");
            intent.putExtra(Intent.EXTRA_TEXT, body);
            context.startActivity(Intent.createChooser(intent, "Choose Email Client"));
        }

        @Override
        public void onPause() {
            //    Toast.makeText(getActivity(), "OnPause", Toast.LENGTH_SHORT).show();


            if (changedValue){
                String adult_filter_value;
                String key_exclude_premium_search_value;

                if (key_adult_filter){
                    adult_filter_value = "1";
                } else {
                    adult_filter_value = "0";
                }
                if (key_exclude_premium_search){
                    key_exclude_premium_search_value = "1";
                } else {
                    key_exclude_premium_search_value = "0";
                }
               // todo enter network call

            }

            super.onPause();
        }
    }

}
/*
 ((VoicemeApplication) getActivity().getApplicationContext())
                        .getWebService().InsertNewSettings(key_user_age,key_user_gender,
                        adult_filter_value, key_exclude_premium_search_value,
                        MySharedPreferences.getUserId(preferences))
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(new RetryWithDelay(3,2000))
                        .subscribe(new BaseSubscriber<SuccessResponse>() {
                            @Override
                            public void onNext(SuccessResponse response) {

                            }
                            @Override
                            public void onError(Throwable e) {
                                try {
                                    Timber.e(e.getMessage());
                                    //   Toast.makeText(ChangeProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                        });
 */
