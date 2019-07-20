package in.becandid.app.becandid.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;

public class StartPage06 extends BaseActivity {
    private Button get_location;
 //   private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Provides the entry point to the Fused Location Provider API.
     */

    /**
     * Represents a geographical locationPOJO.
     */
    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page04);

        get_location = (Button) findViewById(R.id.get_location);

        toolbar.setTitle("Location");


        get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermissions()) {
                   // MySharedPreferences.registerLocation(preferences, "true");
                    requestPermissions();
                }
            }
        });
    }

    @Override
    protected void setUp() {

    }

    /**
     * Provides a simple way of getting a device's locationPOJO and is well suited for
     * applications that do not require a fine-grained locationPOJO and that do not need locationPOJO
     * updates. Gets the best and most recent locationPOJO currently available, which may be null
     * in rare cases when a locationPOJO is not available.
     * <p>
     * Note: this method should be called after locationPOJO permission has been granted.
     */

    private void gotoNextPage(){
        Intent intent = new Intent(StartPage06.this, DiscoverActivity.class);
        startActivity(intent);
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(StartPage06.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
        //    Log.i(TAG, "Displaying permission rationale to provide additional context.");

        } else {
       //     Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_skip_menu_location, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skip_menu_location:
                gotoNextPage();
                return true;
            default:
                return true;
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
     //   Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
      //          Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                gotoNextPage();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.

            }
        }
    }
}
