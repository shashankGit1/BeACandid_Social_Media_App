package in.becandid.app.becandid.ui.discover;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.angads25.toggle.LabeledSwitch;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.infrastructure.Account;
import in.becandid.app.becandid.ui.postDetails.PostsDetailsActivity;
import in.becandid.app.becandid.ui.group.UserGroupDetails;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.chat.MessageActivity;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.base.VoicemeApplication;
import in.becandid.app.becandid.ui.userpost.EditPost;
import in.becandid.app.becandid.ui.userpost.ReportAbuseActivity;

import static in.becandid.app.becandid.ui.base.Constants.CONSTANT_PREF_FILE;


/**
 * Created by ericbasendra on 02/12/15.
 */
public class LatestListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    public List<PostsModel> dataSet;
   // private PostsModel postsModel;
    private Context mContext;
    private int mLastPosition = 5;
    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;
    private String errorMsg;

    // private static PostMoreButton postMoreButton;

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataSet.size() - 1;
        PostsModel result = getItem(position);

        if (result != null) {
            dataSet.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        dataSet.clear();
        notifyDataSetChanged();
    }


    public void add(PostsModel r) {
        dataSet.add(r);
        notifyItemInserted(dataSet.size() - 1);
    }

    public void addAll(List<PostsModel> moveResults) {
        for (PostsModel result : moveResults) {
            add(result);
        }
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new PostsModel());
     //   addItem(dataSet.size(), new PostsModel() );
    }


    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(dataSet.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    public LatestListAdapter(Context context) {
        this.mContext = context;
        dataSet = new ArrayList<>();
    }

    public PostsModel getItem(int index) {
        if (dataSet != null && dataSet.get(index) != null) {
            return dataSet.get(index);
        } else {
            throw new IllegalArgumentException("Item with index " + index + " doesn't exist, dataSet is " + dataSet);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

 /*   @Override
    public int getItemViewType(int position) {
        // my earlier setting
      //  return dataSet.get((dataSet.size() - 1)).getImage() ? VIEW_ITEM : VIEW_PROG; // 0, = true, 1 = false
        return dataSet.get(position).getImage() ? VIEW_ITEM : VIEW_PROG; // false = 0, true = 1
    } */



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        PostsModel object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.getType()) {
                case PostsModel.TEXT_TYPE:

                    ((TextTypeViewHolderText) holder).bind(object); // true - image is coming

                    break;
                case PostsModel.IMAGE_TYPE:

                    ((ImageTypeViewHolder) holder).bind(object); // true - image is coming

                    break;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).getType()) {
            case 0:
                return PostsModel.TEXT_TYPE;
            case 1:
                return PostsModel.IMAGE_TYPE;
            default:
                return -1;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case PostsModel.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_text_posts_cardview, parent, false);
                return new TextTypeViewHolderText(view, mContext);
            case PostsModel.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_posts_cardview, parent, false);
                return new ImageTypeViewHolder(view, mContext);

        }
        return null;


    }

   /* @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        switch (viewType) {
            case 0: return new TextTypeViewHolderText(LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.list_item_text_posts_cardview, parent, false), mContext); // true
            case 1: return new TextTypeViewHolderText(LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.list_item_text_posts_cardview, parent, false), mContext); // false
            default:
                throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }
    } */





    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class TextTypeViewHolderText extends PostsTextCardViewHolder {

     //   boolean isPlaying = false;
    //    private boolean doDislike;
        private PopupMenu popupMenu;
        private Menu menu;
        private Bitmap bmp;


        public TextTypeViewHolderText(View itemView, Context context) {
            super(itemView, context);
        }

        @Override
        protected void categoryClicked(View v) {
            Intent intent = new Intent(v.getContext(), UserGroupDetails.class);
            intent.putExtra(Constants.GROUPID, dataItem.getGroup_id());
            v.getContext().startActivity(intent);
        }

        @Override
        protected void cardBackground(View view) {
      //      if (processLoggedState(view))
      //          return;


            Intent intent = new Intent(view.getContext(), PostsDetailsActivity.class);
            intent.putExtra(Constants.POST_BACKGROUND, dataItem.getIdPosts());
            intent.putExtra(Constants.IDUSERNAME, dataItem.getIdUserName());
            view.getContext().startActivity(intent);
        }


        @Override
        protected void moreClick(View view){
         //   bmp = drawTextToBitmap(view.getContext(), dataItem.getTextStatus());

            SharedPreferences preferences;
            preferences = ((VoicemeApplication) itemView.getContext().getApplicationContext()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);


            if (MySharedPreferences.getUserId(preferences) == null){
                Toast.makeText(itemView.getContext(), "You are not logged In", Toast.LENGTH_SHORT).show();
            } else {
                popupMenu = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.pop_menu, popupMenu.getMenu());
                //    this.menu = popupMenu.getMenu();

                if (MySharedPreferences.getUserId(preferences).equals(dataItem.getIdUserName())){
                    popupMenu.getMenu().setGroupVisible(R.id.main_menu_group, true);
                } else {
                    popupMenu.getMenu().setGroupVisible(R.id.main_menu_group, false);
                }



             //   popupMenu.getMenu().setGroupVisible(R.id.main_menu_group, true);


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_post:

                                Intent editIntent = new Intent(itemView.getContext(), EditPost.class);
                                editIntent.putExtra(Constants.IDPOST, dataItem.getIdPosts());
                                editIntent.putExtra(Constants.IDUSERNAME, dataItem.getIdUserName());
                                editIntent.putExtra(Constants.ADMIN_ID, dataItem.getAdmin_id());
                                editIntent.putExtra(Constants.STATUS_POST, dataItem.getTextStatus());
                              /*  if (dataItem.getAudioFileLink().isEmpty() || dataItem.getAudioFileLink() == null){
                                    Timber.e("No audio attached");
                                } else {
                                    editIntent.putExtra(Constants.AUDIO, dataItem.getAudioFileLink());
                                } */

                                itemView.getContext().startActivity(editIntent);
                                return true;

                                case R.id.send_message_menu:

                                    final Random rand = new Random();
                                    int randomUserId = rand.nextInt(54) + 1;
                                    String randomUserIdd = String.valueOf(randomUserId);

                                    Intent intent = new Intent(itemView.getContext(), MessageActivity.class);
                                    intent.putExtra(Constants.RANDOM_SENDER_ID, randomUserIdd); // username inside message
                                    intent.putExtra(Constants.SENDER_USERID, dataItem.getIdUserName() ); // username inside message
                                    intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, dataItem.getUser_name_random()); // direct username in chat
                                    intent.putExtra(Constants.RANDOM_RECEIVER_ID, dataItem.getId_user_name_random()); // direct username id in chat
                                    intent.putExtra(Constants.RECEIVER_USERID, MySharedPreferences.getUserId(preferences)); // direct random user id in chat
                                    intent.putExtra(Constants.RANDOM_RECEIVER_AVATAR, dataItem.getAvatar_url_random()); // direct random user id in chat
                                    intent.putExtra(Constants.POSTID, dataItem.getIdPosts()); // direct random user id in chat

                                    intent.putExtra(Constants.YES, dataItem.getIdUserName());
                                    intent.putExtra(Constants.USERNAME, dataItem.getUser_name_random());
                                    itemView.getContext().startActivity(intent);
                                return true;

                            case R.id.report_post:

                                Intent reportIntent = new Intent(itemView.getContext(), ReportAbuseActivity.class);
                                reportIntent.putExtra(Constants.IDPOST, dataItem.getIdPosts());
                                reportIntent.putExtra(Constants.IDUSERNAME, dataItem.getIdUserName());
                                reportIntent.putExtra(Constants.STATUS_POST, dataItem.getTextStatus());
                                itemView.getContext().startActivity(reportIntent);
                                return true;

                           /*  case R.id.make_popular:

                                sendPopularPost((VoicemeApplication) itemView.getContext().getApplicationContext(), dataItem.getIdPosts(), dataItem.getTextStatus());

                                return true;

                            case R.id.remove_make_popular:
                                removePopularPost((VoicemeApplication) itemView.getContext().getApplicationContext(), dataItem.getIdPosts());

                                return true;

                             */


                            default:
                                return false;

                        }
                    }
                });
                popupMenu.show();

            }

        }

        @Override
        protected void switchButton(LabeledSwitch labeledSwitch, boolean isOn, View view) {

            if (labeledSwitch.isOn()){

                postMessage.setText("This post is flagged as Abusive");
                switchbutton.setOn(true);
            } else {
                postMessage.setText(dataItem.getTextStatus());

                switchbutton.setOn(false);

            }
        }

        @Override
        protected void likeButtonMethod(View view) {
       //     likeCounter = Integer.parseInt(like_counter.getText().toString());

            if (like_button_true){

            //    Toast.makeText(itemView.getContext(), "unLiked", Toast.LENGTH_SHORT).show();
                like_button_true = false;
                unlikeMethod();
            } else {

                if (hug_button_true){
                    hug_button_true = false;

                    unHugMethod();
                }

                like_button_true = true;


            /*    String userId = MySharedPreferences.getUserId(recyclerviewpreferences);
                String sendLike = "senderid@" + userId + "_contactId@" +
                        dataItem.getIdUserName() + "_postId@" + dataItem.getIdPosts()  + "_click@" + "1"; */


                ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendLikeNotify( dataItem.getIdPosts(), "1"));

                likeCounter++;

                new_counter_like_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(likeCounter)));
                likeButtonMain.setImageResource(R.drawable.ic_heart_red);
            }

        }

        private void unlikeMethod() {
         //   mLatestInterface.sendLikeToServer();

       //     sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext(), 0, 1,  "clicked unlike button");

            ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendLikeNotify(dataItem.getIdPosts(), "0"));

          //  ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendSadEntry(dataItem.getIdUserName(), dataItem.getIdPosts()));

            // sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext());
            likeCounter--;
            like_button_true = false;
            new_counter_like_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(likeCounter)));
            likeButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart));
        }


        protected void hugButtonMethod(View view) {
        //    hugCounter = Integer.parseInt(hug_counter.getText().toString());

            if (hug_button_true){
                hug_button_true = false;


                unHugMethod();
            } else {

                if (like_button_true){
                    like_button_true = false;

                    unlikeMethod();
                }

                hug_button_true = true;

                hugCounter++;
             //   mLatestInterface.sendLikeToServer();


                ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendSadEntry(dataItem.getIdPosts(), "1"));

                //    sendHugToServer((VoicemeApplication) itemView.getContext().getApplicationContext(), 0,  "clicked hug button");

                new_counter_hug_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(hugCounter)));
                HugButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart_broken_red));
            }
        }

        private void unHugMethod() {
          //  mLatestInterface.sendLikeToServer();

          //    sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext(), 1, 0,  "clicked unlike button");

            ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendSadEntry(dataItem.getIdPosts(), "0"));

            hugCounter--;
            hug_button_true = false;

            new_counter_hug_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(hugCounter)));
            HugButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart_broken));
        }

        // Todo code to calculate text length - https://stackoverflow.com/questions/4794484/calculate-text-size-according-to-width-of-text-area
    }

    public static class ImageTypeViewHolder extends PostsImageCardViewHolder {


        boolean isPlaying = false;
        private boolean doDislike;
        private PopupMenu popupMenu;
        private Menu menu;
        private Bitmap bmp;


        public ImageTypeViewHolder(View itemView, Context context) {
            super(itemView, context);
        }


        @Override
        protected void categoryClicked(View v) {
            Intent intent = new Intent(v.getContext(), UserGroupDetails.class);
            intent.putExtra(Constants.GROUPID, dataItem.getGroup_id());
            v.getContext().startActivity(intent);
        }

        @Override
        protected void cardBackground(View view) {
            //      if (processLoggedState(view))
            //          return;


            Intent intent = new Intent(view.getContext(), PostsDetailsActivity.class);
            intent.putExtra(Constants.POST_BACKGROUND, dataItem.getIdPosts());
            intent.putExtra(Constants.IDUSERNAME, dataItem.getIdUserName());
            view.getContext().startActivity(intent);
        }


        @Override
        protected void switchButton(LabeledSwitch labeledSwitch, boolean isOn, View view) {

            if (labeledSwitch.isOn()){

                list_item_posts_message_image.setImageDrawable(itemView.getResources().getDrawable(R.drawable.picture));
                postMessage.setText("This post is flagged as Abusive");
                switchbutton.setOn(true);
            } else {
                postMessage.setText(dataItem.getTextStatus());
                list_item_posts_message_image.setImageURI(Uri.parse(dataItem.getImage_url()));

                switchbutton.setOn(false);

            }

        }

        @Override
        protected void moreClick(View view){
          //  bmp = drawTextToBitmap(view.getContext(), dataItem.getTextStatus());

            SharedPreferences preferences;
            preferences = ((VoicemeApplication) itemView.getContext().getApplicationContext()).getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);


            if (MySharedPreferences.getUserId(preferences) == null){
                Toast.makeText(itemView.getContext(), "You are not logged In", Toast.LENGTH_SHORT).show();
            } else {
                popupMenu = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.pop_menu, popupMenu.getMenu());
                //    this.menu = popupMenu.getMenu();

                if (MySharedPreferences.getUserId(preferences).equals(dataItem.getIdUserName())){
                    popupMenu.getMenu().setGroupVisible(R.id.main_menu_group, true);
                } else {
                    popupMenu.getMenu().setGroupVisible(R.id.main_menu_group, false);
                }

            //    popupMenu.getMenu().setGroupVisible(R.id.main_menu_group, true);



                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_post:

                                Intent editIntent = new Intent(itemView.getContext(), EditPost.class);
                                editIntent.putExtra(Constants.IDPOST, dataItem.getIdPosts());
                                editIntent.putExtra(Constants.IDUSERNAME, dataItem.getIdUserName());
                                editIntent.putExtra(Constants.ADMIN_ID, dataItem.getAdmin_id());
                                editIntent.putExtra(Constants.STATUS_POST, dataItem.getTextStatus());
                             /*   if (dataItem.getAudioFileLink().isEmpty() || dataItem.getAudioFileLink() == null){
                                    Timber.e("No audio attached");
                                } else {
                                    editIntent.putExtra(Constants.AUDIO, dataItem.getAudioFileLink());
                                } */

                                itemView.getContext().startActivity(editIntent);
                                return true;

                       /*     case R.id.make_popular:

                                sendPopularPost((VoicemeApplication) itemView.getContext().getApplicationContext(), dataItem.getIdPosts(), dataItem.getTextStatus());

                                return true; */

                            case R.id.send_message_menu:
                                final Random rand = new Random();
                                int randomUserId = rand.nextInt(54) + 1;
                                String randomUserIdd = String.valueOf(randomUserId);

                                Intent intent = new Intent(itemView.getContext(), MessageActivity.class);
                                intent.putExtra(Constants.RANDOM_SENDER_ID, randomUserIdd); // username inside message
                                intent.putExtra(Constants.SENDER_USERID, dataItem.getIdUserName()); // username inside message
                                intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, dataItem.getUser_name_random()); // direct username in chat
                                intent.putExtra(Constants.RANDOM_RECEIVER_ID, dataItem.getId_user_name_random()); // direct username id in chat
                                intent.putExtra(Constants.RECEIVER_USERID, MySharedPreferences.getUserId(preferences) ); // direct random user id in chat
                                intent.putExtra(Constants.RANDOM_RECEIVER_AVATAR, dataItem.getAvatar_url_random()); // direct random user id in chat
                                intent.putExtra(Constants.POSTID, dataItem.getIdPosts()); // direct random user id in chat

                                intent.putExtra(Constants.YES, dataItem.getIdUserName());
                                intent.putExtra(Constants.USERNAME, dataItem.getUser_name_random());
                                itemView.getContext().startActivity(intent);

                                return true;

                       /*     case R.id.remove_make_popular:
                                removePopularPost((VoicemeApplication) itemView.getContext().getApplicationContext(), dataItem.getIdPosts());

                                return true; */

                            case R.id.report_post:

                                Intent reportIntent = new Intent(itemView.getContext(), ReportAbuseActivity.class);
                                reportIntent.putExtra(Constants.IDPOST, dataItem.getIdPosts());
                                reportIntent.putExtra(Constants.IDUSERNAME, dataItem.getIdUserName());
                                reportIntent.putExtra(Constants.STATUS_POST, dataItem.getTextStatus());
                                itemView.getContext().startActivity(reportIntent);
                                return true;


                            default:
                                return false;

                        }
                    }
                });
                popupMenu.show();

            }

        }


        @Override
        protected void likeButtonMethod(View view) {
            //     likeCounter = Integer.parseInt(like_counter.getText().toString());

            if (like_button_true){

                //    Toast.makeText(itemView.getContext(), "unLiked", Toast.LENGTH_SHORT).show();
                like_button_true = false;
                unlikeMethod();
            } else {
                if (hug_button_true){
                    hug_button_true = false;

                    unHugMethodWithoutNetwork();
                }

                like_button_true = true;
                likeCounter++;

                ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendLikeNotify(dataItem.getIdPosts(), "1"));

                new_counter_like_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(likeCounter)));
                likeButtonMain.setImageResource(R.drawable.ic_heart_red);
            }

        }

        private void unlikeMethod() {
          //  mLatestInterface.getRandomName();
            ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendLikeNotify(dataItem.getIdPosts(), "0"));

         //   sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext(), 0, 1,  "clicked unlike button");
            // sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext());
            likeCounter--;
            like_button_true = false;
            new_counter_like_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(likeCounter)));
            likeButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart));
        }

        private void unlikeMethodWithoutNetwork() {
          //  mLatestInterface.getRandomName();
          //  ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendLikeNotify(dataItem.getIdPosts(), "0"));

         //   sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext(), 0, 1,  "clicked unlike button");
            // sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext());
            likeCounter--;
            like_button_true = false;
            new_counter_like_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(likeCounter)));
            likeButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart));
        }

        @Override
        protected void hugButtonMethod(View view) {
            //    hugCounter = Integer.parseInt(hug_counter.getText().toString());

            if (hug_button_true){

                hug_button_true = false;

                unHugMethod();
            } else {

                if (like_button_true){
                    like_button_true = false;

                    unlikeMethodWithoutNetwork();
                }
                hug_button_true = true;


                hugCounter++;
            //    mLatestInterface.getRandomName();

                ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendSadEntry(dataItem.getIdPosts(), "1"));


                //  sendHugToServer((VoicemeApplication) itemView.getContext().getApplicationContext(), 0, "clicked hug button");

                new_counter_hug_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(hugCounter)));
                HugButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart_broken_red));
            }
        }

        private void unHugMethod() {

            hugCounter--;
            hug_button_true = false;

          //  mLatestInterface.getRandomName();
            ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendSadEntry(dataItem.getIdPosts(), "0"));


//            sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext(), 1, 0,  "clicked unlike button");


            new_counter_hug_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(hugCounter)));
            HugButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart_broken));
        }

        private void unHugMethodWithoutNetwork() {

            hugCounter--;
            hug_button_true = false;

          //  mLatestInterface.getRandomName();
           // ((VoicemeApplication) itemView.getContext().getApplicationContext()).getBus().send(new Account.sendSadEntry(dataItem.getIdPosts(), "0"));


//            sendUnlikeToServer((VoicemeApplication) itemView.getContext().getApplicationContext(), 1, 0,  "clicked unlike button");


            new_counter_hug_number.setText(String.valueOf(NumberFormat.getIntegerInstance().format(hugCounter)));
            HugButtonMain.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_heart_broken));
        }

        // Todo code to calculate text length - https://stackoverflow.com/questions/4794484/calculate-text-size-according-to-width-of-text-area

        @Override
        protected void clear() {

        }
    }


}


