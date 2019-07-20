package in.becandid.app.becandid.ui.chat;

import java.io.File;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface ChatMvpPresenter <V extends ChatMvpView> extends MvpPresenter<V> {

    void blockUserInsertOnline(String currentUserId, String userToBeBlockedId);

    void deleteEntireChatOnline(String id_conversation);
    void insertCustomNameOnline(String id_conversation, String user_id, String username, String avatar_url);
    void getAllChatMessagesOnline(String user_one, String token);


    void sendTextNotification(String senderId, String receiverId, String chatText, String chatImage, String receiverAnonymous, String id_posts, String token);
    void delete_chat(String id_conversation_reply);
    void getChatMessages(String from_user_id, String user_id, String token, String to_user_id, String page);
    void postImageUpload(File file);
   // void getChatMessages02(String user_one, String user_two, String id_posts, String token, String page);
}

