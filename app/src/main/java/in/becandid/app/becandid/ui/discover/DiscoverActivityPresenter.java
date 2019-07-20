package in.becandid.app.becandid.ui.discover;

import com.androidnetworking.error.ANError;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.PostLikesResponse;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class DiscoverActivityPresenter <V extends DiscoverActivityMvpView> extends BasePresenter<V>
        implements DiscoverActivityMvpPresenter<V> {

    @Inject
    public DiscoverActivityPresenter(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }



    @Override
    public void postSaveToken(String id_user_name, String pushnotificationToken) {
        //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .postSaveToken(id_user_name, pushnotificationToken)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(SuccessResponse response) throws Exception {

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

    ///String user_id, String post_id, String like, String token
    @Override
    public void sendLikeNotification(String user_id, String post_id, String like, String token) {
        getCompositeDisposable().add(getDataManager()
                .post_likes(user_id, post_id,like, token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<PostLikesResponse>() {
                    @Override
                    public void accept(PostLikesResponse response) throws Exception {

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
    public void sendSadNotification(String senderId, String postId, String sad, String token) {
        getCompositeDisposable().add(getDataManager()
                .post_sad(senderId, postId,sad,token)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<PostLikesResponse>() {
                    @Override
                    public void accept(PostLikesResponse response) throws Exception {

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
    public void postMakeAdult(String id_posts, String adult_filter) {
        getCompositeDisposable().add(getDataManager()
                .postMakeAdult(id_posts, adult_filter)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(SuccessResponse response) throws Exception {

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
    public void updateTimeOnline(String token, String user_id) {
        getCompositeDisposable().add(getDataManager()
                .updateTimeOnline(token, user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(SuccessResponse response) throws Exception {


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
}
