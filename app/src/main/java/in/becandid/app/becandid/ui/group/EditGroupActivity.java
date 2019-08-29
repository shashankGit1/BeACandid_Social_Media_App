package in.becandid.app.becandid.ui.group;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.drawee.view.SimpleDraweeView;
import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.Constants;
import timber.log.Timber;

public class EditGroupActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, EditGroupMvpView {
    Button group_name_button_desc_edit;
    SimpleDraweeView ivProfileImage_edit;
    TextView group_name_create_edit;
    TextView group_name_category_desc;
    TextView group_name_create_desc_edit;
    EditText etNameOfGroup_edit;
    EditText group_name_text_desc_edit;
    String group_id;
    private boolean uploadimage = false;

    private MaterialSpinner spinner_category_edit;

    List<CommunityGroupPojo> response;
    private View progressFrame;

    protected LabeledSwitch switchbutton_group;
    private Uri resultUri;

    private File tempOutputFile;
    private String group_selected;
    private static final int REQUEST_SELECT_IMAGE = 100;
    private String image_created_location;
    String[] categories = { "Select Category", "Politics", "Celebrities", "Music", "Technology", "Fashion", "Business", "School", "Art", "Photography", "LGBT", "Relationships", "Sports", "Funny", "Confessions", "Personal", "Sex", "Family", "Work", "Faith", "Food", "Entertainment", "Women Issues", "Health", "Men Issues", "Science", "Teens"};

    @Inject
    EditGroupMvpPresenter<EditGroupMvpView> mPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(EditGroupActivity.this);


        toolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Edit Group");


        progressFrame = findViewById(R.id.post_edit_progressBar);


        group_name_button_desc_edit = (Button) findViewById(R.id.group_name_button_desc_edit);
        ivProfileImage_edit = (SimpleDraweeView) findViewById(R.id.ivProfileImage_edit);
        switchbutton_group = findViewById(R.id.switchbutton_group);
        group_name_create_edit = (TextView) findViewById(R.id.group_name_create_edit);
        group_name_create_desc_edit = (TextView) findViewById(R.id.group_name_create_desc_edit);
        group_name_category_desc = (TextView) findViewById(R.id.group_name_category_desc);
        etNameOfGroup_edit = (EditText) findViewById(R.id.etNameOfGroup_edit);
        group_name_text_desc_edit = (EditText) findViewById(R.id.group_name_text_desc_edit);

        tempOutputFile = new File(getExternalCacheDir(), "temp-profile_image.jpg");

        spinner_category_edit = (MaterialSpinner) findViewById(R.id.spinner_category_edit);

        ivProfileImage_edit.setOnClickListener(this);
        group_name_button_desc_edit.setOnClickListener(this);

        Intent intent = getIntent();
        group_id = intent.getStringExtra(Constants.GROUP_ID_NEW);

        spinner_category_edit.setItems(categories);

        spinner_category_edit.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                group_selected = String.valueOf(position);
                  Toast.makeText(EditGroupActivity.this, "position: " + group_selected, Toast.LENGTH_SHORT).show();
            }
        });
        spinner_category_edit.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                //  Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                group_selected = null;
            }
        });


        switchbutton_group.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                // Implement your switching logic here
                if (labeledSwitch.isOn()){

                    subscribeToTopic(String.valueOf("GROUP_" + group_id));

                    labeledSwitch.setOn(true);
                } else {

                    unsubscribeToTopic(String.valueOf("GROUP_" + group_id));


                    labeledSwitch.setOn(false);

                }
            }
        });


        try {
            mPresenter.getGroupSpecific(group_id, MySharedPreferences.getUserId(preferences),"1");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void unsubscribeToTopic(String topic){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = String.valueOf("You have Unsubscribed to stop notifications from this post");
                        if (!task.isSuccessful()) {
                            msg = String.valueOf("Unsubscribed to topic " + topic);
                        }
                    //    Toast.makeText(EditGroupActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void subscribeToTopic(String topic){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                FirebaseMessaging.getInstance().subscribeToTopic(topic)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = String.valueOf("Subscribed to topic " + topic);
                                if (!task.isSuccessful()) {
                                    msg = String.valueOf("Failed to Subscribed to topic " + topic);
                                }
                                //   Toast.makeText(PostsDetailsActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }, 100);


    }

    @Override
    protected void setUp() {

    }





    private void askPermissions() {
        int requestCode = 11234;
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(EditGroupActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(EditGroupActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditGroupActivity.this, permissions, requestCode);

        }
        else {
            changeAvatar();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 11234:
                // If request is cancelled, the result arrays are empty
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    changeAvatar();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            default:
                //If request result array is not empty
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

            // other 'switch' lines to check for other
            // permissions this app might request
    }

    private void changeProfileRequest() {
        //  ActivityUtils.openGallery(this);
        askPermissions();
    }

    private void changeAvatar() {
        List<Intent> otherImageCaptureIntent = new ArrayList<>();
        List<ResolveInfo> otherImageCaptureActivities =
                getPackageManager().queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
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

                image_created_location = tempOutputFile.getPath();

                uploadimage = true;

                Timber.e(String.valueOf(Uri.fromFile(tempOutputFile)));
                ivProfileImage_edit.setImageResource(0);

                ivProfileImage_edit.setImageDrawable(getResources().getDrawable(R.drawable.image_accepted));

                // avatarProgressFrame.setVisibility(View.VISIBLE);
                // getBus.post(new Account.ChangeAvatarRequest(Uri.fromFile(tempOutputFile)));
            }
        }
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
        uCrop.start(EditGroupActivity.this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.group_name_button_desc_edit:

                if (uploadimage) {
                    try {
                        uploadFile(Uri.fromFile(tempOutputFile));
                        //   postStatus(locationValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    mPresenter.editGroup(group_selected, etNameOfGroup_edit.getText().toString(), group_name_text_desc_edit.getText().toString(),  group_id);

                }

             //   String id_categories, String name, String group_image_url, String group_description, String group_id

                break;
            case R.id.ivProfileImage_edit:

                changeProfileRequest();

                break;
        }
    }

    private void uploadFile(Uri fileUri) {

        progressFrame.setVisibility(View.VISIBLE);
        File file = new File(fileUri.getPath());

        mPresenter.postImageUpload(file);



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void getGroupSpecific(List<CommunityGroupPojo> response) {

        etNameOfGroup_edit.setText(response.get(0).getGroupName());
        group_name_text_desc_edit.setText(response.get(0).getGroupDescription());
        ivProfileImage_edit.setImageURI(response.get(0).getGroupImageUrl());

        spinner_category_edit.setSelectedIndex(Integer.parseInt(response.get(0).getIdCategories()));

        if (response.get(0).isGroup_notif()){
            switchbutton_group.setOn(true);
        }
        group_selected = response.get(0).getIdCategories();
        progressFrame.setVisibility(View.GONE);
    }


    @Override
    public void editGroupSuccess(SuccessResponse response) {

        File file = new File(String.valueOf(resultUri));
        if (file.exists()) {
            file.delete();
        }

        Intent intent = new Intent(EditGroupActivity.this, UserGroupDetails.class);
        intent.putExtra(Constants.GROUPID, group_id);
        startActivity(intent);

        progressFrame.setVisibility(View.GONE);
      //  Toast.makeText(PostActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getImageUrl(String image) {

        progressFrame.setVisibility(View.VISIBLE);

        try {
            mPresenter.editGroupWithImage(group_selected, etNameOfGroup_edit.getText().toString(), image, group_name_text_desc_edit.getText().toString(),  group_id);


            //postStatus(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();

        super.onDestroy();
    }
}

/*
private void loadGroupDetails() throws Exception {

        application.getWebService()
                .getGroupPosts(groupId,  MySharedPreferences.getUserId(preferences), currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<List<PostsModel>>() {
                    @Override
                    public void onNext(List<PostsModel> response) {
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        hideErrorView();

                        user_category_tag.setText(response.get(0).getCategory());
                        toolbar.setTitle(response.get(0).getName());

                        Log.e("RESPONSE:::", "Size===" + response.size());

                        activityInteractionAdapter.addAll(response);
                        arraylistCurrent(response);

                        try {
                            loadGroupDetails(response.get(0).getGroup_id());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        progressFrame.setVisibility(View.GONE);
                        showErrorView(e);
                    }
                });
    }
 */
