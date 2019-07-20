package in.becandid.app.becandid.ui.group;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.base.MvpView;
import in.becandid.app.becandid.ui.discover.DiscoverActivity;
import in.becandid.app.becandid.ui.userpost.PostActivity;
import timber.log.Timber;

import static in.becandid.app.becandid.ui.base.Constants.explicitWords;

public class CreateGroupDescActivity extends BaseActivity implements MvpView {
    EditText group_name_text_desc;
    Button btNext;
    TextView tvSkipOption;
    ImageView ivProfileImage;
    private File tempOutputFile;
    private static final int REQUEST_SELECT_IMAGE = 100;
    private String image_Url = null;
    private String group_name;
    private String image_created_location;
    private String adultWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_desc);

        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        group_name_text_desc = (EditText) findViewById(R.id.group_name_text_desc);
        group_name = getIntent().getStringExtra("name");
        group_name_text_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    btNext.setBackgroundColor(Color.parseColor("#51c05c"));
                    btNext.setTextColor(Color.parseColor("#ffffff"));
                    btNext.setEnabled(true);

                } else {
                    btNext.setBackgroundColor(Color.parseColor("#33000000"));
                    btNext.setEnabled(false);
                }
            }
        });
        tempOutputFile = new File(getExternalCacheDir(), "temp-profile_image.jpg");

        btNext = (Button) findViewById(R.id.group_name_button_desc);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage();
            }
        });

        tvSkipOption = (TextView) findViewById(R.id.tvSkipOption);
        tvSkipOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage();
            }
        });
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    changeProfileRequest();
                }
                catch (Exception e){
                    Log.e("Pic error", e.getMessage());
                }
            }
        });
    }

    @Override
    protected void setUp() {

    }

    public void nextPage() {

        try {
            if (isAdultContent(group_name_text_desc.getText().toString())) {
                new AlertDialog.Builder(CreateGroupDescActivity.this)
                        .setTitle("The post contains adult content!")
                        .setMessage("As per Google Guidelines, No adult content can posted publicly. Adult word found in your status : " + adultWord)
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            } else {
                Intent intent = new Intent(CreateGroupDescActivity.this, CreateGroupTagActivity.class);
                intent.putExtra("desc", group_name_text_desc.getText().toString());
                if (image_created_location != null){
                    intent.putExtra("locationPOJO", image_created_location);
                } else {
                    intent.putExtra("locationPOJO", "null");
                }
                intent.putExtra("name", group_name);
                startActivity(intent);
                finish();

            }
            //   postStatus(locationValue);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    private boolean isAdultContent(String postDesc) {
        for (String str : explicitWords)
            if (postDesc.contentEquals(str)){
                adultWord = str;
                return true;
            }
        return false;
    }


    private void changeProfileRequest() {
        //  ActivityUtils.openGallery(this);

        askPermissions();
      //  ActivityUtils.cameraPermissionGranted(this);
     //   changeAvatar();
    }

    private void askPermissions() {
        int requestCode = 1489;
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PermissionChecker.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1489: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    changeAvatar();


                } else {

                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
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


                image_created_location = tempOutputFile.getPath();

                Timber.e(String.valueOf(Uri.fromFile(tempOutputFile)));
                ivProfileImage.setImageResource(0);

                ivProfileImage.setImageURI(Uri.fromFile(tempOutputFile));

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
        uCrop.start(CreateGroupDescActivity.this);
    }


    private void setImageFileUrl(String imageUrl) {
        this.image_Url = imageUrl;
    }
}
