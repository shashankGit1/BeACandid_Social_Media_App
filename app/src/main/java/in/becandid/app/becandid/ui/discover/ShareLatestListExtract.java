package in.becandid.app.becandid.ui.discover;

public class ShareLatestListExtract {
    /*
private void shareImage() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(itemView.getContext());
            LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View dialogView = inflater.inflate(R.layout.custom_dialog_share_image, null);
            dialogBuilder.setView(dialogView);

            AlertDialog b = dialogBuilder.create();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager)itemView.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            b.getWindow().setLayout(width, 110);
            b.show();

            ImageView facebook = (ImageView) dialogView.findViewById(R.id.facebook);
            ImageView twitter = (ImageView) dialogView.findViewById(R.id.twitter);
            ImageView linkIn = (ImageView) dialogView.findViewById(R.id.linkIn);
            ImageView Instagram = (ImageView) dialogView.findViewById(R.id.instagram);
            ImageView pinist = (ImageView) dialogView.findViewById(R.id.pinest);
            ImageView tumber = (ImageView) dialogView.findViewById(R.id.tumber);
            ImageView more = (ImageView) dialogView.findViewById(R.id.more);

            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(), bmp, "HelloAndroid", "com.facebook.katana");
                    }

                    b.cancel();
                }
            });

            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.twitter.android");
                    }

                    b.cancel();
                }
            });

            linkIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.linkedin.android");
                    }

                    b.cancel();
                }
            });

            Instagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.instagram.android");
                    }

                    b.cancel();
                }
            });

            pinist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.pinterest");
                    }

                    b.cancel();
                }
            });
            tumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.tumblr");
                    }

                    b.cancel();
                }
            });
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(Intent.createChooser(sharedIntentMaker(), "Choose an app"));

                    b.cancel();
                }
            });
        }

        private Intent sharedIntentMaker() {
            String sharebody = String.valueOf(dataItem.getUser_name_random() + " " + "said:"
                    + " " + dataItem.getTextStatus() + " " + "inside Voiceme Android App. " +
                    "You can download from www.beacandid.com/candid?Post=" + dataItem.getIdPosts() + "");
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
            return shareIntent;
        }
 */


/*
 private Bitmap drawTextToBitmap(Context mContext, String text) {

            int textLength = text.length();
            int MAX_CHARCTER_IN_LINE = 60; //Text character count in one line

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) itemView.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            //int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            float text_x = width-40; //Image width taken in consideration
            float text_y = 500; //Image height taken in consideration
            double THRESHOLD_DIP = 30.0;

            final float scale = mContext.getResources().getDisplayMetrics().density;

            int mThreshold = (int) (THRESHOLD_DIP * scale + 0.5f);

            String[] splited = text.split("\\s+");
            double longest = 0;
            for (String s : splited) {
                if (s.length() > longest) {
                    longest = s.length();
                }
            }
            if (longest > MAX_STRING_LENGTH) {
                double ratio = (double) MAX_STRING_LENGTH / longest;
                mThreshold = (int) ((THRESHOLD_DIP * ((float) ratio)) * scale + 0.5f);
            }

            int noOfLines = Math.abs(textLength/MAX_CHARCTER_IN_LINE);
            for (int i=0; i<noOfLines; i++) {
                if(text_y > 130)
                    text_y -= 30;
            }

            //Bitmap bitmap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.image);
            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);

            TextPaint mTextPaint = new TextPaint();
            mTextPaint.setColor(Color.WHITE);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTextSize(mThreshold);
            StaticLayout mTextLayout = new StaticLayout(text, mTextPaint, canvas.getWidth()-40, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

            canvas.save();

            canvas.translate(text_x, text_y);
            mTextLayout.draw(canvas);
            canvas.restore();

            return bitmap;
        }

        private void createDirectoryAndSaveFile(Context context, Bitmap imageToSave, String fileName, String PackageApp) {

            File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");

            if (!direct.exists()) {
                File wallpaperDirectory = new File("/sdcard/DirName/");
                wallpaperDirectory.mkdirs();
            }

            File file = new File(new File("/sdcard/DirName/"), fileName + ".jpg");
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                Uri uri = Uri.parse("file://" + file.getAbsolutePath());
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setPackage(PackageApp);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.setType("image/png");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(share, "Share image File"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 */

/*
private Bitmap drawTextToBitmap(Context mContext, String text) {

            int textLength = text.length();
            int MAX_CHARCTER_IN_LINE = 60; //Text character count in one line

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) itemView.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            //int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            float text_x = width-40; //Image width taken in consideration
            float text_y = 500; //Image height taken in consideration
            double THRESHOLD_DIP = 30.0;

            final float scale = mContext.getResources().getDisplayMetrics().density;

            int mThreshold = (int) (THRESHOLD_DIP * scale + 0.5f);

            String[] splited = text.split("\\s+");
            double longest = 0;
            for (String s : splited) {
                if (s.length() > longest) {
                    longest = s.length();
                }
            }
            if (longest > MAX_STRING_LENGTH) {
                double ratio = (double) MAX_STRING_LENGTH / longest;
                mThreshold = (int) ((THRESHOLD_DIP * ((float) ratio)) * scale + 0.5f);
            }

            int noOfLines = Math.abs(textLength/MAX_CHARCTER_IN_LINE);
            for (int i=0; i<noOfLines; i++) {
                if(text_y > 130)
                    text_y -= 30;
            }

            //Bitmap bitmap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.image);
            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);

            TextPaint mTextPaint = new TextPaint();
            mTextPaint.setColor(Color.WHITE);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTextSize(mThreshold);
            StaticLayout mTextLayout = new StaticLayout(text, mTextPaint, canvas.getWidth()-40, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

            canvas.save();

            canvas.translate(text_x, text_y);
            mTextLayout.draw(canvas);
            canvas.restore();

            return bitmap;
        }

        private void createDirectoryAndSaveFile(Context context, Bitmap imageToSave, String fileName, String PackageApp) {

            File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");

            if (!direct.exists()) {
                File wallpaperDirectory = new File("/sdcard/DirName/");
                wallpaperDirectory.mkdirs();
            }

            File file = new File(new File("/sdcard/DirName/"), fileName + ".jpg");
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                Uri uri = Uri.parse("file://" + file.getAbsolutePath());
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setPackage(PackageApp);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.setType("image/png");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(share, "Share image File"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 */

/*
private void shareImage() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(itemView.getContext());
            LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View dialogView = inflater.inflate(R.layout.custom_dialog_share_image, null);
            dialogBuilder.setView(dialogView);

            AlertDialog b = dialogBuilder.create();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager)itemView.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            b.getWindow().setLayout(width, 110);
            b.show();

            ImageView facebook = (ImageView) dialogView.findViewById(R.id.facebook);
            ImageView twitter = (ImageView) dialogView.findViewById(R.id.twitter);
            ImageView linkIn = (ImageView) dialogView.findViewById(R.id.linkIn);
            ImageView Instagram = (ImageView) dialogView.findViewById(R.id.instagram);
            ImageView pinist = (ImageView) dialogView.findViewById(R.id.pinest);
            ImageView tumber = (ImageView) dialogView.findViewById(R.id.tumber);
            ImageView more = (ImageView) dialogView.findViewById(R.id.more);

            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(), bmp, "HelloAndroid", "com.facebook.katana");
                    }

                    b.cancel();
                }
            });

            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.twitter.android");
                    }

                    b.cancel();
                }
            });

            linkIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.linkedin.android");
                    }

                    b.cancel();
                }
            });

            Instagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.instagram.android");
                    }

                    b.cancel();
                }
            });

            pinist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.pinterest");
                    }

                    b.cancel();
                }
            });
            tumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityUtils.deleteAudioFile(itemView.getContext())){
                        createDirectoryAndSaveFile(itemView.getContext(),bmp, "HelloAndroid", "com.tumblr");
                    }

                    b.cancel();
                }
            });
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(Intent.createChooser(sharedIntentMaker(), "Choose an app"));

                    b.cancel();
                }
            });
        }

        private Intent sharedIntentMaker() {
            String sharebody = String.valueOf(dataItem.getUser_name_random() + " " + "said:"
                    + " " + dataItem.getTextStatus() + " " + "inside Voiceme Android App. " +
                    "You can download from www.beacandid.com/candid?Post=" + dataItem.getIdPosts() + "");
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
            return shareIntent;
        }
 */
}
