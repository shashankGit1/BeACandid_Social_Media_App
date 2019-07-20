package in.becandid.app.becandid.ui.userpost;

import com.androidnetworking.error.ANError;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.ImageUrl;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PostPresenter <V extends PostMvpView> extends BasePresenter<V>
        implements PostMvpPresenter<V> {

    @Inject
    public PostPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getJoinedGroups(String user_id) {
     //   getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getJoinedGroups(user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<GroupUser>>() {
                    @Override
                    public void accept(@NonNull List<GroupUser> blogResponse)
                            throws Exception {

                        getMvpView().getJoinedGroups(blogResponse);

//                        getMvpView().hideLoading();
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
    public void postStatusPending(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter) {

        getCompositeDisposable().add(getDataManager()
                .postStatusPending(user_id, post_text, image_url, group_id, cat_id, feeling_id, type, adult_filter)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(@NonNull UserResponse blogResponse)
                            throws Exception {

                       // getMvpView().postStatus(blogResponse);

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
    public void postImageUpload(File file) {
 //       getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .postImageUpload(file)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ImageUrl>() {
                    @Override
                    public void accept(ImageUrl response) throws Exception {

                        getMvpView().getImageUrl(response);
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

    @Override
    public void updatePost(String category, String action, String text_status) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .updatePost(category, action, text_status)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(@NonNull SuccessResponse blogResponse)
                            throws Exception {

                        getMvpView().updatePost(blogResponse);

                        getMvpView().hideLoading();
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
    public void reportAbuse(String id_user_name, String sender_user_id, String id_posts, String token, String message) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .reportAbuse(id_user_name, sender_user_id, id_posts, token, message)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(@NonNull SuccessResponse blogResponse)
                            throws Exception {

                        getMvpView().reportAbuse(blogResponse);

                        getMvpView().hideLoading();
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
}
