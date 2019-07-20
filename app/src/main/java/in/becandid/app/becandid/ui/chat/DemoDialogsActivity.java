package in.becandid.app.becandid.ui.chat;

import android.os.Bundle;
import androidx.annotation.Nullable;

import in.becandid.app.becandid.chat_design.commons.ImageLoader;
import in.becandid.app.becandid.chat_design.dialogs.DialogsListAdapter;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.chat02.model.Dialog;

/**
 * Created by harishpc on 6/1/2017.
 */
public abstract class DemoDialogsActivity extends BaseActivity
        implements DialogsListAdapter.OnDialogClickListener<Dialog>,
        DialogsListAdapter.OnDialogLongClickListener<Dialog> {

    protected ImageLoader imageLoader;
    protected DialogsListAdapter<Dialog> dialogsAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     /*   imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                //If you using another library - write here your way to load image
                //   Picasso.with(DialogDetailsActivity.this).load(url).placeholder(getResources().getDrawable(R.drawable.user)).error(getResources().getDrawable(R.drawable.user)).into(imageView);

                if (url != null){
                    if (url.isEmpty()){
                        Glide.with(getContext()).load(R.drawable.user).into(imageView);

                      //  Picasso.get().load(R.drawable.user).centerCrop().fit().into(imageView);

                     //   Picasso.with(DemoDialogsActivity.this).load(R.drawable.user).fit().centerInside().into(imageView);
                        } else {
                        Glide.with(getContext()).load(url).into(imageView);

                   //     Picasso.get().load(url).centerCrop().fit().into(imageView);

                     //   Picasso.with(DemoDialogsActivity.this).load(url).fit().centerInside().into(imageView);
                        }
                } else {
                    Glide.with(getContext()).load(url).into(imageView);

                 //   Picasso.get().load(url).centerCrop().fit().into(imageView);

                   // Picasso.with(DemoDialogsActivity.this).load(url).fit().centerInside().into(imageView);
                    }


            }
        }; */
    }

    @Override
    public void onDialogLongClick(Dialog dialog) {

    }
}
