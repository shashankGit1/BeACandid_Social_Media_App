package in.becandid.app.becandid.ui.chat;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import in.becandid.app.becandid.di.component.ActivityComponent;
import in.becandid.app.becandid.R;
import in.becandid.app.becandid.chat_design.commons.ImageLoader;
import in.becandid.app.becandid.chat_design.commons.models.IMessage;
import in.becandid.app.becandid.chat_design.dialogs.DialogsList;
import in.becandid.app.becandid.chat_design.dialogs.DialogsListAdapter;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.SuccessResponseChat;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.ui.base.BaseFragment;
import in.becandid.app.becandid.ui.base.Constants;
import in.becandid.app.becandid.infrastructure.MySharedPreferences;
import in.becandid.app.becandid.ui.chat02.model.Dialog;
import in.becandid.app.becandid.ui.chat02.model.Message;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment implements ChatMvpView {
    protected ImageLoader imageLoader;
    protected DialogsListAdapter<Dialog> dialogsListAdapter = null;
    @Inject
    ChatMvpPresenter<ChatMvpView> mPresenter;
    private List<Dialog> messages = null;
    private Menu menu;
    private View view;
    //  private View progressFrame;
    private View progressFrame;
    private Button error_btn_retry;
    private DialogsList dialogsListView;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        progressFrame = view.findViewById(R.id.dialog_details);
        error_btn_retry = (Button) view.findViewById(R.id.error_btn_retry);

        dialogsListView = (DialogsList) view.findViewById(R.id.dialogsList);

     /*   dialogsListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        */

        //   dialogsListAdapter = new DialogsListAdapter<>(imageLoader);
        try {
            mPresenter.getAllChatMessagesOnline(MySharedPreferences.getUserId(preferences) , MySharedPreferences.getFireBaseToken(preferences));


            // getAllChatMessages();
       //     chatMessages();

        } catch (Exception e) {
            e.printStackTrace();
        }

        error_btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mPresenter.getAllChatMessagesOnline(MySharedPreferences.getUserId(preferences) , MySharedPreferences.getFireBaseToken(preferences));

                 //   getAllChatMessages(MySharedPreferences.getUserId(preferences) , MySharedPreferences.getUserToken(preferences));
                  //  chatMessages();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imageLoader = new ImageLoader() {
            public void loadImage(ImageView imageView, String url, Object payload) {
                if (url != null) {
                    if (url.isEmpty()) {
                      //  Glide.with(getActivity()).load(R.drawable.user).into(imageView);

                         Picasso.get().load(R.drawable.user).fit().centerCrop().into(imageView);
                        //  Picasso.with(view.getContext()).load(R.drawable.user).fit().centerCrop().into(imageView);
                    } else {
                       // Glide.with(getActivity()).load(url).into(imageView);

                             Picasso.get().load(url).centerCrop().fit().into(imageView);
                        //      Picasso.with(view.getContext()).load(url).fit().centerCrop().into(imageView);
                    }
                }
            }
        };



        return view;
    }

    private void dialogInit(List<Dialog> response) {

       /* dialogsListAdapter = new DialogsListAdapter<>(
                R.layout.item_custom_dialog_view_holder,
                CustomDialogViewHolder.class,
                imageLoader); */

        dialogsListAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, imageLoader);


        dialogsListAdapter.setItems(response);


        // dialogsListAdapter = new DialogsListAdapter<>(imageLoader);

        dialogsListAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener<Dialog>() {
            @Override
            public void onDialogClick(Dialog dialog) {
                // Todo add methods to get user ID of the other user, add own ID
                //   startActivity(new Intent(DialogDetailsActivity.this, MessageActivity.class));



                Intent intent = new Intent(getActivity(), MessageActivity.class);

              //  intent.putExtra(Constants.RANDOM_SENDER_USERNAME, dialog.getLastMessage().getUser().getName()); // username inside message
                intent.putExtra(Constants.POSTID, dialog.getLastMessage().getPostId()); // username inside message
              //  intent.putExtra(Constants.RANDOM_SENDER_ID, dialog.getLastMessage().getUser().getSenderAnonymous()); // username inside message
                intent.putExtra(Constants.RANDOM_RECEIVER_USERNAME, dialog.getDialogName()); // direct username in chat
                intent.putExtra(Constants.RANDOM_RECEIVER_ID, getSecondUserRandom(dialog)); // direct username id in chat
                intent.putExtra(Constants.RANDOM_RECEIVER_AVATAR, getSecondUserAvatar(dialog)); // direct username id in chat
                intent.putExtra(Constants.SENDER_USERID , getSecondUser(dialog)); // direct random user id in chat
                intent.putExtra(Constants.RECEIVER_USERID, MySharedPreferences.getUserId(preferences)); // direct random user id in chat

              //  intent.putExtra(Constants.YES, dialog.getId());
              //  intent.putExtra(Constants.USERNAME, dialog.getDialogName());
                startActivity(intent);

            }
        });

        // Todo add random username here to open the chat activity for privat message
     /*   dialogsListAdapter.setOnUsernameClicked(new DialogsListAdapter.OnClickUsername<ChatDialogPojo>() {
            @Override
            public void onClick(ChatDialogPojo dialog) {
                Intent intent = new Intent(getActivity(), SecondProfile.class);
                intent.putExtra(Constants.SECOND_PROFILE_ID, dialog.getId());
                startActivity(intent);
            }
        }); */



        dialogsListAdapter.setOnDialogLongClickListener
                (new DialogsListAdapter.OnDialogLongClickListener<Dialog>() {
                    @Override
                    public void onDialogLongClick(Dialog dialog01) {

                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                        builderSingle.setIcon(R.drawable.message_icon_new);
                        builderSingle.setTitle("Select One Option:-");

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);
                        arrayAdapter.add("Block User");
                        arrayAdapter.add("Rename User");
                        arrayAdapter.add("Delete Chat");

                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case 0:

                                        //  Toast.makeText(getActivity(), "selected 0", Toast.LENGTH_SHORT).show();
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("Do you want to Block this user completely?")
                                                .setMessage("Note: After blocking this user, this user can't send message to you")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //    Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                                                        progressFrame.setVisibility(View.VISIBLE);

                                                        try {
                                                            mPresenter.blockUserInsertOnline(MySharedPreferences.getUserId(preferences), getSecondUser(dialog01));

                                                            // blockUserInsert();
                                                            //  blockUserInsert();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                })
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //   Log.d("MainActivity", "Aborting mission...");
                                                    }
                                                })
                                                .show();
                                        break;
                                    case 1:
                                        //    Toast.makeText(getActivity(), "selected 1", Toast.LENGTH_SHORT).show();

                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                                        alertDialog.setTitle("Enter New Name Username Below");

                                        final EditText input = new EditText(getActivity());
                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.MATCH_PARENT);
                                        input.setLayoutParams(lp);
                                        input.setText(dialog01.getDialogName());
                                        alertDialog.setView(input);
                                        // alertDialog.setIcon(R.drawable.key);

                                        alertDialog.setPositiveButton("YES",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        progressFrame.setVisibility(View.VISIBLE);

                                                        try {
                                                            //String id_conversation, String user_id, String username, String avatar_url
                                                            mPresenter.insertCustomNameOnline(dialog01.getId(), getSecondUser(dialog01), input.getText().toString(),getSecondUserAvatar(dialog01)  );

                                                            //   insertCustomName();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }


                                                    }
                                                });
                                        alertDialog.setNegativeButton("NO",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });

                                        alertDialog.show();

                                        break;
                                    case 2:
                                        //    Toast.makeText(getActivity(), "selected 2", Toast.LENGTH_SHORT).show();
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("Do you want to Delete complete chat?")
                                                .setMessage("Note: After deletion, this chat can't be recovered. It will be deleted for both the users")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        progressFrame.setVisibility(View.VISIBLE);

                                                        //    Loga.d("MainActivity", "Sending atomic bombs to Jupiter");

                                                        progressFrame.setVisibility(View.VISIBLE);
                                                        try {
                                                            mPresenter.deleteEntireChatOnline(dialog01.getId());

                                                            //   deleteEntireChat();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                })
                                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //   Log.d("MainActivity", "Aborting mission...");
                                                    }
                                                })
                                                .show();
                                        break;

                                }
                            }
                        });
                        builderSingle.show();

                    }
                }


        );


        dialogsListView.setAdapter(dialogsListAdapter);
    }

    private String getSecondUser(Dialog dialog){
        if(!dialog.getUsers().get(0).getId().equalsIgnoreCase(MySharedPreferences.getUserId(preferences))){
            return dialog.getUsers().get(0).getId();
        } else {
            return dialog.getUsers().get(1).getId();

        }
    }

    private String getSecondUserAvatar(Dialog dialog){
        if(!dialog.getUsers().get(0).getId().equalsIgnoreCase(MySharedPreferences.getUserId(preferences))){
            return dialog.getUsers().get(0).getAvatar();
        } else {
            return dialog.getUsers().get(1).getAvatar();

        }
    }

    private String getSecondUserRandom(Dialog dialog){
        if(!dialog.getUsers().get(0).getId().equalsIgnoreCase(MySharedPreferences.getUserId(preferences))){
            return dialog.getUsers().get(0).getRandomId();
        } else {
            return dialog.getUsers().get(1).getRandomId();

        }
    }


    /* Menu for deleting
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.chat_dialog_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_dialog:

                //
                break;
        }
        return true;
    }
   */

    private void onNewMessage(String dialogId, IMessage message) {
        if (!dialogsListAdapter.updateDialogWithMessage(dialogId, message)) {
            //Dialog with this ID doesn't exist, so you can create new Dialog or update all dialogs list
        }
    }

    private void onNewDialog(Dialog dialog) {
        // Todo add network calls to add new dialog
        dialogsListAdapter.addItem(dialog);
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();

        super.onDestroyView();
    }

    @Override
    public void insertCustomName(SuccessResponse successResponse) {
        Toast.makeText(getActivity(), "Successfully updated the username", Toast.LENGTH_LONG).show();

        progressFrame.setVisibility(View.VISIBLE);

        mPresenter.getAllChatMessagesOnline(MySharedPreferences.getUserId(preferences) , MySharedPreferences.getFireBaseToken(preferences));

       // progressFrame.setVisibility(View.GONE);
    }

    @Override
    public void deleteEntireChat(SuccessResponse successResponse) {
        Toast.makeText(getActivity(), "Successfully deleted the chat", Toast.LENGTH_LONG).show();

        progressFrame.setVisibility(View.VISIBLE);

        mPresenter.getAllChatMessagesOnline(MySharedPreferences.getUserId(preferences) , MySharedPreferences.getFireBaseToken(preferences));


    }

    @Override
    public void blockUserInsert(UserResponse userResponse) {
        Toast.makeText(getActivity(), "Successfully Blocked the User", Toast.LENGTH_LONG).show();
        progressFrame.setVisibility(View.GONE);

    }

    @Override
    public void getAllChatMessages(List<Dialog> response) {
        messages = response;
        dialogInit(response);


        progressFrame.setVisibility(View.GONE);
    }

    @Override
    public void sendTextNotification(SuccessResponseChat response) {

    }

    @Override
    public void delete_chat(SuccessResponse userResponse) {

    }

    @Override
    public void getImageUrl(String imageUrl) {

    }

    @Override
    public void getChatMessages(List<Message> messagePojos) {

    }

  /*  @Override
    public void getChatMessages02(List<Message> messagePojos) {

    }
    */

    @Override
    public void onResume() {
        super.onResume();

        progressFrame.setVisibility(View.VISIBLE);

        mPresenter.getAllChatMessagesOnline(MySharedPreferences.getUserId(preferences) , MySharedPreferences.getFireBaseToken(preferences));


    }
}

/*
private void insert_new_user_name(String username, String avatar_url, String senderId, String receiverId, ChatDialogPojo dialog01) throws Exception {
        application.getWebService()
                .insert_new_custom_name(username, avatar_url, senderId, receiverId)
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<SuccessResponse>() {
                    @Override
                    public void onNext(SuccessResponse response) {
                        //     Toast.makeText(DialogDetailsActivity.this, response.get(0).getId(), Toast.LENGTH_SHORT).show();
                        //    MessagePojo pojo = response.get(0).getMessage();

                        Toast.makeText(getActivity(), "Successfully updated the username", Toast.LENGTH_LONG).show();

                        try {
                            chatMessages();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        progressFrame.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressFrame.setVisibility(View.GONE);
                    }

                });
    }

    private void delete_chat(String user_id, String second_user_id) throws Exception {
        application.getWebService()
                .deleteEntireChat(user_id, second_user_id, MySharedPreferences.getUserToken(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<SuccessResponse>() {
                    @Override
                    public void onNext(SuccessResponse response) {
                        //     Toast.makeText(DialogDetailsActivity.this, response.get(0).getId(), Toast.LENGTH_SHORT).show();
                        //    MessagePojo pojo = response.get(0).getMessage();

                        Toast.makeText(getActivity(), "Successfully deleted the chat", Toast.LENGTH_LONG).show();


                        progressFrame.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressFrame.setVisibility(View.GONE);
                    }

                });
    }

    private void blockUserInsert(String secondUserId) {
        application.getWebService()
                .blockUserInsert(MySharedPreferences.getUserId(preferences), secondUserId, MySharedPreferences.getUserToken(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribe(new BaseSubscriber<UserResponse>() {
                    @Override
                    public void onNext(UserResponse response) {
                        //     followers.setText(String.valueOf(response.size()));
                        Toast.makeText(getActivity(), "Successfully Blocked the User", Toast.LENGTH_LONG).show();
                        // Timber.d("Message from server" + response);
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            Timber.e(e.getMessage());
                            //     Toast.makeText(SecondProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
    }

    private void chatMessages() throws Exception {
        application.getWebService()
                .getAllChatMessages(MySharedPreferences.getUserId(preferences) , MySharedPreferences.getUserToken(preferences))
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3,2000))
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<List<ChatDialogPojo>>() {
                    @Override
                    public void onNext(List<ChatDialogPojo> response) {
                        //     Toast.makeText(DialogDetailsActivity.this, response.get(0).getId(), Toast.LENGTH_SHORT).show();
                        //    MessagePojo pojo = response.get(0).getMessage();
                        messages = response;
                        dialogInit(response);


                        progressFrame.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Throwable e){
                        e.printStackTrace();
                        progressFrame.setVisibility(View.GONE);
                    }

                });
    }
 */
