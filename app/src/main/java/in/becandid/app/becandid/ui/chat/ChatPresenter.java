package in.becandid.app.becandid.ui.chat;

import com.androidnetworking.error.ANError;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.ImageUrl;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.SuccessResponseChat;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.ui.chat02.model.Dialog;
import in.becandid.app.becandid.ui.chat02.model.Message;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ChatPresenter<V extends ChatMvpView> extends BasePresenter<V>
        implements ChatMvpPresenter<V> {

    @Inject
    public ChatPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void insertCustomNameOnline(String id_conversation, String user_id, String username, String avatar_url) {
     //   getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .sendCustomNameInsert(id_conversation, user_id, username, avatar_url)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(SuccessResponse response) throws Exception {

                        getMvpView().insertCustomName(response);

                        // todo add data and loop to get all friends list
                    /*    getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                     //   getMvpView().openMainActivity();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                      //  getMvpView().hideLoading();

                        // handle the login error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }

    @Override
    public void deleteEntireChatOnline(String id_conversation) {
     //   getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .deleteEntireChat(id_conversation)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(SuccessResponse response) throws Exception {

                        getMvpView().deleteEntireChat(response);


                        // todo add data and loop to get all friends list
                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                      //  getMvpView().openMainActivity();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                    //    getMvpView().hideLoading();

                        // handle the login error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }

    @Override
    public void blockUserInsertOnline(String currentUserId, String userToBeBlockedId) {
      //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .blockUserInsert(currentUserId, userToBeBlockedId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(UserResponse response) throws Exception {
                            getMvpView().blockUserInsert(response);

                        // todo add data and loop to get all friends list
                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                   //     getMvpView().hideLoading();
                  //      getMvpView().openMainActivity();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the login error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }

    @Override
    public void getAllChatMessagesOnline(String user_one, String token) {
      //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getAllChats(user_one, token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Dialog>>() {
                    @Override
                    public void accept(List<Dialog> response) throws Exception {


                    getMvpView().getAllChatMessages(response);
                        // todo add data and loop to get all friends list
                      /*  getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                    //    getMvpView().hideLoading();
                    //    getMvpView().openMainActivity();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the login error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }

    @Override
    public void sendTextNotification(String senderId, String receiverId, String chatText, String chatImage, String receiverAnonymous, String id_posts, String token)  {
        //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .sendTextNotification(senderId, receiverId, chatText, chatImage, receiverAnonymous,id_posts,token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponseChat>() {
                    @Override
                    public void accept(@NonNull SuccessResponseChat blogResponse)
                            throws Exception {


                        getMvpView().sendTextNotification(blogResponse);

                        //  getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));

    }

    @Override
    public void delete_chat(String id_conversation_reply) {
        //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .deleteChat(id_conversation_reply)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(@NonNull SuccessResponse blogResponse)
                            throws Exception {

                        getMvpView().delete_chat(blogResponse);


                        //    getMvpVigetAllChatMessagesOnlineew().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }

    @Override
    public void getChatMessages(String user_one, String user_two, String id_posts, String token, String page) {
        //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getChatMessages(user_one, user_two, id_posts, token, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Message>>() {
                    @Override
                    public void accept(@NonNull List<Message> blogResponse)
                            throws Exception {

                        getMvpView().getChatMessages(blogResponse);


                        //   getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        List<Message> response = new ArrayList<>();
                        getMvpView().getChatMessages(response);

                        // handle the error here
                      /*  if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        } */
                    }
                }));
    }

   /* @Override
    public void getChatMessages02(String user_one, String user_two, String id_posts, String token, String page) {
        //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getChatMessages02(user_one, user_two, id_posts, token, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<Message>>() {
                    @Override
                    public void accept(@NonNull List<Message> blogResponse)
                            throws Exception {

                        getMvpView().getChatMessages02(blogResponse);


                        //   getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        List<Message> response = new ArrayList<>();
                        getMvpView().getChatMessages(response);

                        // handle the error here
                      /*  if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }*/

    @Override
    public void postImageUpload(File file) {
        //       getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .postImageUpload(file)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ImageUrl>() {
                    @Override
                    public void accept(ImageUrl response) throws Exception {

                        getMvpView().getImageUrl(response.getLink());
                        //   getMvpView().getUserJoinedGroupsFirstPage(response);

                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                        if (!isViewAttached()) {
                            return;
                        }

                        //         getMvpView().hideLoading();
                        //   getMvpView().openMainActivity();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                        //  getMvpView().hideLoading();

                        // handle the login error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}
