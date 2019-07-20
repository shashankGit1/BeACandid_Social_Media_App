package in.becandid.app.becandid.ui.profile;

import com.androidnetworking.error.ANError;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.ContactAddResponse;
import in.becandid.app.becandid.dto.LoginResponse;
import in.becandid.app.becandid.dto.MainResponse;
import in.becandid.app.becandid.dto.ProfileMain;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ProfilePagePresenter <V extends ProfilePageMvpView> extends BasePresenter<V>
        implements ProfilePageMvpPresenter<V> {

    @Inject
    public ProfilePagePresenter(DataManager dataManager,
                                 SchedulerProvider schedulerProvider,
                                 CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getUserProfileOnline(String user_id) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getUserProfile(user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ProfileMain>() {
                    @Override
                    public void accept(@NonNull ProfileMain blogResponse)
                            throws Exception {

                        getMvpView().getUserProfile(blogResponse);

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
    public void onFacebookLoginClick(String name, String email, String userId, String deviceId, String socialNetwork) {
        //validate email and password
     /*   if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        } */
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .doLoginResponse(name, email,userId,deviceId,socialNetwork)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse response) throws Exception {

                        getMvpView().sendData(response);

                        getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );


                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();
                      //  getMvpView().openMainActivity();

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
    public void faceboookFriends(String accessToken, String limit){

        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getFacebookFriends(accessToken, limit)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MainResponse>() {
                    @Override
                    public void accept(MainResponse response) throws Exception {

                        getMvpView().facebookFriends(response);


                        // todo add data and loop to get all friends list
                   /*     getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();
                      //  getMvpView().openMainActivity();

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
    public void faceboookFriends02(String access_token, String limit, String after) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getFacebookFriends02(access_token, limit, after)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MainResponse>() {
                    @Override
                    public void accept(MainResponse response) throws Exception {

                        getMvpView().facebookFriends02(response);


                        // todo add data and loop to get all friends list
                   /*     getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();
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
    public void sendFacebookFriends(String id_user_name, String facebook_id) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .sendFacebookFriends(id_user_name, facebook_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ContactAddResponse>() {
                    @Override
                    public void accept(ContactAddResponse response) throws Exception {

                        getMvpView().sendFacebookFriends(response);


                        // todo add data and loop to get all friends list
                   /*     getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();
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
    public void sendAuthorisation(String secretWord, String user_id) {

        getCompositeDisposable().add(getDataManager()
                .getAuthorisedAccess(secretWord, user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(SuccessResponse response) throws Exception {

                        getMvpView().getAccess(response);

                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();
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


}
