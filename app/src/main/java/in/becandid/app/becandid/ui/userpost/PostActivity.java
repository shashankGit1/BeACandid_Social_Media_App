package in.becandid.app.becandid.ui.userpost;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.content.res.ResourcesCompat;

import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.autocomplete.Autocomplete;
import in.becandid.app.becandid.autocomplete.AutocompleteCallback;
import in.becandid.app.becandid.autocomplete.AutocompletePresenter;
import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.autocomplete.UserPresenter;
import in.becandid.app.becandid.dto.ImageUrl;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.ui.group.CommunityGroupPojo;
import in.becandid.app.becandid.ui.group.CreateGroupActivity;
import timber.log.Timber;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PostActivity extends BaseActivity implements
        AdapterView.OnItemSelectedListener, View.OnClickListener, PostMvpView {

    private static final int REQUEST_SELECT_IMAGE = 1800;
    private static final int PERMISSION_REQUEST_CODE = 1121;

    private static final String[] TAKE_PICTURE_PERM = {CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};
    private String[] allCategories;
    List<CommunityGroupPojo> listOfGroups;

    @BindView(R.id.switchbutton_post_status) LabeledSwitch switchbutton_post_status;
    @BindView(R.id.create_post_circle) ImageView create_post_circle;
    @BindView(R.id.uploading_image_loading) ProgressBar uploading_image_loading;
    @BindView(R.id.single) EditText single;
    EditText etPostDesc;
    @BindView(R.id.ic_action_image_upload) ImageView ic_action_image_upload;
    @BindView(R.id.status_group_post) View progressFrame;
    @BindView(R.id.btPost) Button btPost;
    @BindView(R.id.ivBackArrow) ImageView ivBackArrow;

    private String adultWord;


    private Autocomplete userAutocomplete;
    //  private String image_Url = null;


    int groupPosition;
    //  private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    private double Longitude;
    private double Latitude;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    String GroupId;
    String category_id;
    //  String locationValue;

    private File tempOutputFile; //storing the profile_image temporarily while we crop it.
    private Uri resultUri; //storing the profile_image temporarily while we crop it.
    protected boolean selectedCategory = false;
    List<GroupUser> groups_joined;
    private Uri fileUri;
    private boolean uploadimage = false;
    private String isAdult = "false";

    /**
     * Static list to check for explicit words
     */
    private static final List<String> explicitWords = new ArrayList<>(
            Arrays.asList(
                    "assholes", "nudes", "cum", "nudity", "nude", "fuck", "suck", "boobs", "porn",
                    "sex", "horny", "nudes", "pussy", "dick", "anal", "masturbate", "fetish", "cock",
                    "rape", "gangrape", "erect", "penis", "pedos", "pedophile", "bobs", "vagene", "slut",
                    "bitch", "twat", "cunt")
    );

    @Inject
    PostMvpPresenter<PostMvpView> mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(PostActivity.this);


        etPostDesc = (EditText) findViewById(R.id.etPostDesc);

        // spPostCategory = findViewById(R.id.spPostCategory);
        listOfGroups = new ArrayList<>();
        //   mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        tempOutputFile = new File(getExternalCacheDir(), "temp-profile_image.jpg");


        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();

        String group_name = intent.getStringExtra("POST_GROUP_NAME");
        single.setText(group_name);

        create_post_circle.setOnClickListener(this);
        ic_action_image_upload.setOnClickListener(this);
        btPost.setOnClickListener(this);

        switchbutton_post_status.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                // Implement your switching logic here
                switchButton(labeledSwitch, isOn);
            }
        });

        //spPostCategory.setOnItemSelectedListener(PostActivity.this);


        try {
            mPresenter.getJoinedGroups(MySharedPreferences.getUserId(preferences));
            // getGroupsJoined();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setUp() {

    }

    private void switchButton(LabeledSwitch labeledSwitch, boolean isOn) {
        if (labeledSwitch.isOn()) {
            isAdult = "true";
            labeledSwitch.setOn(true);
        } else {
            isAdult = "false";
            labeledSwitch.setOn(false);
        }
    }

    private void setupUserAutocomplete(List<GroupUser> joinedGroup) {
        // EditText edit = (EditText) findViewById(R.id.single);
        float elevation = 6f;
        Drawable backgroundDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.shadow, null);

        AutocompletePresenter<GroupUser> presenter = new UserPresenter(this, joinedGroup);
        AutocompleteCallback<GroupUser> callback = new AutocompleteCallback<GroupUser>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, GroupUser item) {
                editable.clear();
                editable.append(item.getName());
                setGroupId(item.getGroup_id(), item.getId_categories());
                selectedCategory = true;
                return true;
            }

            public void onPopupVisibilityChanged(boolean shown) {
            }
        };

        userAutocomplete = Autocomplete.<GroupUser>on(single)
                .with(elevation)
                .with(backgroundDrawable)
                .with(presenter)
                .with(callback)
                .build();
    }

    private void changeAvatar() {
        List<Intent> otherImageCaptureIntent = new ArrayList<>();
        List<ResolveInfo> otherImageCaptureActivities =
                application.getPackageManager().queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        0); // finding all intents in apps which can handle capture image
        // loop through all these intents and for each of these activities we need to store an intent
        for (ResolveInfo info : otherImageCaptureActivities) { // Resolve info represents an activity on the system that does our work
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.setClassName(info.activityInfo.packageName,
                    info.activityInfo.name); // declaring explicitly the class where we will go
            // where the picture activity dump the image
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempOutputFile));
            otherImageCaptureIntent.add(captureIntent);
        }


        // above code is only for taking picture and letting it go through another app for cropping before setting to imageview
        // now below is for choosing the image from device

        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
        selectImageIntent.setType("image/*");

        Intent chooser = Intent.createChooser(selectImageIntent, "Choose Avatar");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, otherImageCaptureIntent.toArray(
                new Parcelable[otherImageCaptureActivities.size()]));  // add 2nd para as intent of parcelables.

        startActivityForResult(chooser, REQUEST_SELECT_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            tempOutputFile.delete();
            return;
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE) {
                // user selected an image off their device. other condition they took the image and that image is in our tempoutput file
                Uri outputFile;
                uploadimage = true;
                Uri tempFileUri = Uri.fromFile(tempOutputFile);
                // if statement will detect if the user selected an image from the device or took an image
                if (data != null && (data.getAction() == null || !data.getAction()
                        .equals(MediaStore.ACTION_IMAGE_CAPTURE))) {
                    //then it means user selected an image off the device
                    // so we can get the Uri of that image using data.getData
                    outputFile = data.getData();
                    // Now we need to do the crop
                } else {
                    // image was out temp file. user took an image using camera
                    outputFile = tempFileUri;
                    // Now we need to do the crop
                }
                startCropActivity(outputFile);
            } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

                final Uri resultUri = UCrop.getOutput(data);

                this.resultUri = resultUri;

                tempOutputFile = new File(resultUri.getPath());

                Timber.e(String.valueOf(Uri.fromFile(tempOutputFile)));
                ic_action_image_upload.setImageResource(0);

                ic_action_image_upload.setImageDrawable(getResources().getDrawable(R.drawable.image_accepted));

                // avatarProgressFrame.setVisibility(View.VISIBLE);
                // getBus.post(new Account.ChangeAvatarRequest(Uri.fromFile(tempOutputFile)));
            }
        }
    }

    private void uploadFile(Uri fileUri) {
        uploading_image_loading.setVisibility(View.VISIBLE);
        File file = new File(fileUri.getPath());

        this.fileUri = fileUri;

        mPresenter.postImageUpload(file);



    }


    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.withMaxResultSize(960, 1028);
        //    options.setCompressionQuality(DEFAULT_COMPRESS_QUALITY);
        options.setFreeStyleCropEnabled(true);

        //    options.setFreeStyleCropEnabled(mCheckBoxFreeStyleCrop.isChecked());

        uCrop.useSourceImageAspectRatio();
        // uCrop.withAspectRatio(3, 4);

     /*   options.setAspectRatioOptions(1,
                new AspectRatio("1:2", 1, 2),
                new AspectRatio("3:4", 3, 4),
                new AspectRatio("DEFAULT", CropImageView.DEFAULT_ASPECT_RATIO, CropImageView.DEFAULT_ASPECT_RATIO),
                new AspectRatio("16:9", 16, 9),
                new AspectRatio("1:1", 1, 1)); */
        //  options.setImageToCropBoundsAnimDuration(CROP_BOUNDS_ANIMATION_DURATION);
        options.setShowCropGrid(false);
        options.setMaxScaleMultiplier(10.0f);
        return uCrop.withOptions(options);
    }

    private void startCropActivity(@NonNull Uri uri) {

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(tempOutputFile));

        uCrop = advancedConfig(uCrop);
        uCrop.start(PostActivity.this);
    }


    private void setGroupId(String groupId, String category_id) {
        this.GroupId = groupId;
        this.category_id = category_id;
    }

    private void getGroupId(String GroupName) {
        for (int i = 0; i < listOfGroups.size(); i++) {
            if (listOfGroups.get(i).getGroupName().equalsIgnoreCase(GroupName)) {
                GroupId = listOfGroups.get(i).getGroupId();
                category_id = listOfGroups.get(i).getIdCategories();
                setGroupId(GroupId, category_id);
            }
        }
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long id) {
        if (position == 0) {
            selectedCategory = false;
        } else {
            selectedCategory = true;
        }
        groupPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single:
                userAutocomplete.showPopup(" ");
                break;

            case R.id.create_post_circle:
                Intent intent = new Intent(PostActivity.this, CreateGroupActivity.class);
                startActivity(intent);
                break;
            case R.id.ic_action_image_upload:
                askPermissions();

                break;
            case R.id.btPost:
                //   if (ActivityUtils.isLocationPermission(PostActivity.this)){
                //getLastLocation();
                //     } else {
                //     locationValue = "";
                //     }


                if (uploadimage) {

                    if (checkReadPermission()) {

                        try {
                            uploadFile(resultUri);
                            //   postStatus(locationValue);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //main logic or main code

                        // . write your main code to execute, It will execute if the permission is already given.

                    }  else {
                        requestPermission();
                    }

                } else {
                    if (etPostDesc.getText().length() == 0) {
                        Toast.makeText(PostActivity.this, "Please enter some text", Toast.LENGTH_SHORT).show();
                    } else {

                        try {
                            if (isAdultContent(etPostDesc.getText().toString().toLowerCase())) {
                                new AlertDialog.Builder(PostActivity.this)
                                        .setTitle("The post contains adult content!")
                                        .setMessage("As per Google Guidelines, No adult content can posted publicly. Adult word found " + adultWord)
                                        .setCancelable(true)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();
                            } else {
                                mPresenter.postStatus(MySharedPreferences.getUserId(preferences), etPostDesc.getText().toString(), "", GroupId, category_id,
                                        "", "0", isAdult);

                                Intent intent02 = new Intent(PostActivity.this, DiscoverActivity.class);
                                startActivity(intent02);

                            }
                            //   postStatus(locationValue);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

        }
    }

    private boolean checkReadPermission() {
        if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                TAKE_PICTURE_PERM,
                PERMISSION_REQUEST_CODE);
    }

    private void askPermissions() {
        int requestCode = 232;
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        } else {
            changeAvatar();

        }

    }


    /**
     * Checks if the string contains Explicit content
     *
     * @param postDesc
     * @return if postDesc is Adult Content or not
     */
    private boolean isAdultContent(String postDesc) {
        for (String str : explicitWords)
            if (postDesc.contains(str)){
                adultWord = str;
                return true;
            }
        return false;
    }


    @Override
    public void getJoinedGroups(List<GroupUser> response) {
        progressFrame.setVisibility(View.GONE);
        setupUserAutocomplete(response);

    }

    @Override
    public void postStatus(UserResponse response) {
        //   image_Url = null;
        File file = new File(String.valueOf(fileUri));
        if (file.exists()) {
            file.delete();
        }

        Timber.d(response.getMsg());
        progressFrame.setVisibility(View.GONE);
        Toast.makeText(PostActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getImageUrl(ImageUrl response) {
        //   setImageFileUrl(response);

        //VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, and VERY_LIKELY.
        uploading_image_loading.setVisibility(View.GONE);

        if (response.getAdult() > 3){

            new AlertDialog.Builder(PostActivity.this)
                    .setTitle("Adult Content detected by our AI Cloud Software ?")
                    .setMessage("Note: Your content may contain adult images, so it will go for moderation before getting live. Do you want to continue? ")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //    Log.d("MainActivity", "Sending atomic bombs to Jupiter");

                            progressFrame.setVisibility(View.VISIBLE);

                            // unsubscribeToTopic(String.valueOf("POST_" + postId));

                            try {
                                mPresenter.postStatusPending(MySharedPreferences.getUserId(preferences), etPostDesc.getText().toString(), response.getLink(), GroupId, category_id,
                                        "", "1", isAdult);

                                //postStatus(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                //  if (selectedCategory) {
                                Intent intent03 = new Intent(PostActivity.this, DiscoverActivity.class);
                                startActivity(intent03);
                                //   }

                            }

                        }

                    })
                    .setNegativeButton("Cancel Post", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //   Log.d("MainActivity", "Aborting mission...");
                            Intent intent05 = new Intent(PostActivity.this, DiscoverActivity.class);
                            startActivity(intent05);


                        }
                    })
                    .show();


        } else {
            progressFrame.setVisibility(View.VISIBLE);
            String status;

            if (etPostDesc.getText().toString().isEmpty()){
                status = "";
            } else {
                status = etPostDesc.getText().toString();

            }

            try {
                mPresenter.postStatus(MySharedPreferences.getUserId(preferences), status, response.getLink(), GroupId, category_id,
                        "", "1", isAdult);

                //postStatus(response);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //  if (selectedCategory) {
                Intent intent02 = new Intent(PostActivity.this, DiscoverActivity.class);
                startActivity(intent02);
                //   }

            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    try {
                        uploadFile(resultUri);
                        //   postStatus(locationValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions to send chat image",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;


        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PostActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void updatePost(SuccessResponse successResponse) {

    }

    @Override
    public void reportAbuse(SuccessResponse successResponse) {

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }
}

/*
private void getGroupsJoined() throws Exception {
        Log.d(TAG, "loadFirstPage: ");
        application.getWebService()
                .getJoinedGroups(MySharedPreferences.getUserId(preferences))
                .retryWhen(new RetryWithDelay(3, 2000))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<GroupUser>>() {
                    @Override
                    public void onNext(List<GroupUser> response) {
                        //Creating the ArrayAdapter instance having the country list
                        //  response = new ArrayList<>();
                        progressFrame.setVisibility(View.GONE);
                        setupUserAutocomplete(response);
                     /*   listOfGroups = response;
                        ArrayList<String> items = new ArrayList<>();
                        items.add("Choose One Category");
                        for (int i = 0; i < response.size(); i++) {
                            items.add(response.get(i).getGroupName());
                        } */

                     /*   spPostCategory.setAdapter(new ArrayAdapter<String>(PostActivity.this, android.R.layout.simple_list_item_1, items));
                        spPostCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String message = String.format(adapterView.getSelectedItem().toString());
                                if (i == 0){
                                    selectedCategory = false;
                                } else {
                                    selectedCategory = true;
                                }
                                Log.v("DEBUG", message);
                                getGroupId(message);
                            }
                        });
//   spPostCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, response));
                    }
@Override
public void onError(Throwable e) {
        e.printStackTrace();
        }
        });
        }
 */
                     /*
                     private void postStatus(String image_url) throws Exception {
        if (!selectedCategory) {
            Toast.makeText(this, "Please Select one category for the posts", Toast.LENGTH_SHORT).show();
        } else {
            application.getWebService()
                    .postStatus(MySharedPreferences.getUserId(preferences), etPostDesc.getText().toString(), image_url, GroupId, category_id,
                            "", "1", isAdult)
                    .retryWhen(new RetryWithDelay(3, 2000))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<UserResponse>() {
                        @Override
                        public void onNext(UserResponse response) {
                            image_Url = null;
                            File file = new File(String.valueOf(fileUri));
                            if (file.exists()) {
                                file.delete();
                            }
                            Timber.d(response.getMsg());
                            progressFrame.setVisibility(View.GONE);
                            Toast.makeText(PostActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
    private void postTextStatus() throws Exception {
        if (!selectedCategory) {
            Toast.makeText(this, "Please Select one category for the posts", Toast.LENGTH_SHORT).show();
        } else {
            application.getWebService()
                    .postStatus(MySharedPreferences.getUserId(preferences), etPostDesc.getText().toString(), "", GroupId, category_id,
                            "", "0", isAdult)
                    .retryWhen(new RetryWithDelay(3, 2000))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<UserResponse>() {
                        @Override
                        public void onNext(UserResponse response) {
                            progressFrame.setVisibility(View.GONE);
                            Toast.makeText(PostActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
      @SuppressWarnings("MissingPermission")
  /*  private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            Longitude = mLastLocation.getLatitude();
                            Latitude = mLastLocation.getLongitude();
                            locationValue = String.valueOf(Latitude + "," + Longitude);
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                        }
                    }
                });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
               // getLastLocation();
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
    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(PostActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }
                      */

//  private void setImageFileUrl(String imageUrl) {
//       this.image_Url = imageUrl;
// }

   /* private void submitSingleImage(String imageurl) throws Exception {
        application.getWebService()
                .insertImage(imageurl, MySharedPreferences.getUserId(preferences), MySharedPreferences.getUserToken(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<SuccessResponse>() {
                    @Override
                    public void onNext(SuccessResponse response) {
                        //Todo add network call for uploading profile_image file
                      //  Toast.makeText(ChangeProfileActivity.this, "Successfully changed Profile Image", Toast.LENGTH_SHORT).show();
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
    }
     // create RequestBody instance from file
    /*    RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile); */

//mPresenter.postImageUpload(body);

// finally, execute the request
     /*   try {
            application.getWebService()
                    .uploadFile(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<String>() {
                                   @Override
                                   public void onNext(String response) {
                                       Timber.d("file url " + response);
                                       setImageFileUrl(response);
                                       try {
                                           postStatus(response);
                                       } catch (Exception e) {
                                           e.printStackTrace();
                                       }
                                   }
                                   @Override
                                   public void onError(Throwable e) {
                                       e.printStackTrace();
                                   }
                               }
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
     */