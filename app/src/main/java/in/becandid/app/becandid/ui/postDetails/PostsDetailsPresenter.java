package in.becandid.app.becandid.ui.postDetails;

import com.androidnetworking.error.ANError;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.ImageUrl;
import in.becandid.app.becandid.dto.PostLikesResponse;
import in.becandid.app.becandid.dto.PostUserCommentModel;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PostsDetailsPresenter <V extends PostsDetailsMvpView> extends BasePresenter<V>
        implements PostsDetailsMvpPresenter<V> {

    @Inject
    public PostsDetailsPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void postCommentLikeOnline(String id_post_comment, String id_user_name, String id_posts, String comment_likes, String token) {
     //   getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .postCommentLike(id_post_comment, id_user_name, id_posts, comment_likes, token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(@NonNull SuccessResponse blogResponse)
                            throws Exception {

                        getMvpView().postCommentLike(blogResponse);

                   //     getMvpView().hideLoading();
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
    public void postCommentReplyLike(String id_post_comment_reply, String id_user_name, String likes, String id_posts, String token) {
     //   getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .postCommentReplyLike(id_post_comment_reply, id_user_name, likes, id_posts, token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(@NonNull SuccessResponse blogResponse)
                            throws Exception {

                        getMvpView().postCommentReplyLike(blogResponse);

                    //    getMvpView().hideLoading();
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
    public void sendLikeNotification(String user_id, String post_id, String like, String token) {
      //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .post_likes(user_id, post_id, String.valueOf(like), token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<PostLikesResponse>() {
                    @Override
                    public void accept(@NonNull PostLikesResponse blogResponse)
                            throws Exception {

                        getMvpView().sendLikeToServer(blogResponse);

                     //   getMvpView().hideLoading();
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
    public void sendSadToServerOnline(String senderId, String postId, String sad, String token) {
      //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .post_sad(senderId, postId,sad,token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<PostLikesResponse>() {
                    @Override
                    public void accept(@NonNull PostLikesResponse blogResponse)
                            throws Exception {

                        getMvpView().sendSadToServer(blogResponse);

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
    public void getSinglePostOnline(String id_posts, String user_id) {
       // getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getSinglePosts(id_posts, user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(@NonNull List<PostsModel> blogResponse)
                            throws Exception {

                        getMvpView().getSinglePost(blogResponse);

                      //  getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().gotoDiscoverActivity();

                     //   getMvpView().hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }



    @Override
    public void getCommentsReplyOnline(String id_posts, String user_id, String token) {
      //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getCommentsReply(id_posts, user_id, token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostUserCommentModel>>() {
                    @Override
                    public void accept(@NonNull List<PostUserCommentModel> blogResponse)
                            throws Exception {

                        getMvpView().getCommentRepy(blogResponse);

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
    public void sendLikeToServerOnline(String user_id, String post_id, String like, String token) {
        getCompositeDisposable().add(getDataManager()
                .post_likes(user_id, post_id,like, token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<PostLikesResponse>() {
                    @Override
                    public void accept(PostLikesResponse response) throws Exception {

                        getMvpView().sendLikeToServer(response);

                        // todo add data and loop to get all friends list
                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */



                        //getMvpView().hideLoading();
                        // getMvpView().openMainActivity();

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
    public void post_commentsOnline(String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type) {
      //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .post_comments(id_user_name, id_post_user_name, id_posts, message, token, message_image, type)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostUserCommentModel>>() {
                    @Override
                    public void accept(@NonNull List<PostUserCommentModel> blogResponse)
                            throws Exception {

                        getMvpView().getCommentRepy(blogResponse);

                        // comment notification
                        getMvpView().sendCommentNotification();


                        //  getMvpView().post_comments(blogResponse);

                    //    getMvpView().hideLoading();
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
    public void postCommentReply(String id_post_comments, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type) {
        getCompositeDisposable().add(getDataManager()
                .postCommentReply(id_post_comments, id_user_name, id_post_user_name,id_posts, message, token, message_image, type)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostUserCommentModel>>() {
                    @Override
                    public void accept(@NonNull List<PostUserCommentModel> blogResponse)
                            throws Exception {

                        getMvpView().getCommentRepy(blogResponse);

                        getMvpView().sendCommentReplyNotification(id_post_comments);


                       // getMvpView().post_comments(blogResponse);

                        //    getMvpView().hideLoading();
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
    public void deletePendingPost(String id_posts) {

        getCompositeDisposable().add(getDataManager()
                .deletePendingPosts(id_posts)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(@NonNull SuccessResponse blogResponse)
                            throws Exception {

                        //    getMvpView().postStatus(blogResponse);

                        //               getMvpView().hideLoading();
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
    public void postStatus(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter) {
        //     getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .postStatus(user_id, post_text, image_url, group_id, cat_id, feeling_id, type, adult_filter)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(@NonNull UserResponse blogResponse)
                            throws Exception {

                        //    getMvpView().postStatus(blogResponse);

                        //               getMvpView().hideLoading();
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
    public void postCommentReplyReply(String id_post_comments, String id_post_comments_reply, String id_user_name, String id_post_user_name, String id_posts, String message, String token, String message_image, String type) {
        getCompositeDisposable().add(getDataManager()
                .postCommentReplyReply(id_post_comments, id_post_comments_reply, id_user_name, id_post_user_name,id_posts, message, token, message_image, type)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostUserCommentModel>>() {
                    @Override
                    public void accept(@NonNull List<PostUserCommentModel> blogResponse)
                            throws Exception {

                        getMvpView().getCommentRepy(blogResponse);

                        getMvpView().sendCommentReplyReplyNotification(id_post_comments_reply);


                       // getMvpView().post_comments(blogResponse);

                        //    getMvpView().hideLoading();
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
    public void deleteComment(String id_post_comments, String user_id, String id_posts) {
        getCompositeDisposable().add(getDataManager()
                .deleteComment(id_post_comments, user_id, id_posts)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(@NonNull UserResponse blogResponse)
                            throws Exception {

                        getMvpView().deleteComment(blogResponse);

                        //   getMvpView().hideLoading();
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
    public void postOwnerNotification(String id_user_name, String id_posts, String token) {
        getCompositeDisposable().add(getDataManager()
                .post_owner_notification(id_user_name, id_posts, token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(@NonNull SuccessResponse blogResponse)
                            throws Exception {

                        getMvpView().notificationResult(blogResponse);

                        //   getMvpView().hideLoading();
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
    public void postCommentOwnerNotification(String id_user_name, String id_post_comments, String id_posts, String token) {

        getCompositeDisposable().add(getDataManager()
                .post_comment_owner_notification(id_user_name, id_post_comments, id_posts, token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(@NonNull SuccessResponse blogResponse)
                            throws Exception {

                        getMvpView().notificationResult(blogResponse);

                        //   getMvpView().hideLoading();
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
    public void postCommentReplyOwnerNotification(String id_user_name, String id_post_comments_reply, String id_posts, String token) {

        getCompositeDisposable().add(getDataManager()
                .post_comment_owner_reply_notification(id_user_name, id_post_comments_reply, id_posts, token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(@NonNull SuccessResponse blogResponse)
                            throws Exception {

                        getMvpView().notificationResult(blogResponse);

                        //   getMvpView().hideLoading();
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
    public void deleteCommentReply(String id_post_comment_reply, String user_id, String id_posts) {
        getCompositeDisposable().add(getDataManager()
                .deleteCommentReply(id_post_comment_reply, user_id, id_posts)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(@NonNull UserResponse blogResponse)
                            throws Exception {

                        getMvpView().deleteCommentReply(blogResponse);

                        //   getMvpView().hideLoading();
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
    public void postImageUpload(File file) {
        // getMvpView().showLoading();

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


                        //    getMvpView().hideLoading();
                        //   getMvpView().openMainActivity();

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
}
