package in.becandid.app.becandid.ui.chat;

import java.util.List;

import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.SuccessResponseChat;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.ui.base.MvpView;
import in.becandid.app.becandid.ui.chat02.model.Dialog;
import in.becandid.app.becandid.ui.chat02.model.Message;

public interface ChatMvpView extends MvpView {

    void insertCustomName(SuccessResponse successResponse);

    void deleteEntireChat(SuccessResponse successResponse);

    void blockUserInsert(UserResponse userResponse);

    void getAllChatMessages(List<Dialog> chatDialogPojos);

    void sendTextNotification(SuccessResponseChat response);
    void delete_chat(SuccessResponse userResponse);
    void getImageUrl(String imageUrl);
    void getChatMessages(List<Message> messagePojos);
   // void getChatMessages02(List<Message> messagePojos);

}
