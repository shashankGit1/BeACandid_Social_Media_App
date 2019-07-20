package in.becandid.app.becandid.ui.filter;

import com.androidnetworking.error.ANError;

import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PremiumSearchResultPresenter <V extends PremiumSearchResultMvpView> extends BasePresenter<V>
        implements PremiumSearchResultMvpPresenter<V> {

    @Inject
    public PremiumSearchResultPresenter(DataManager dataManager,
                              SchedulerProvider schedulerProvider,
                              CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getAgeGenderPost01(String id_user_name, String gender, String user_date_of_birth, String premiumuser, int page) {

        //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getAgeGenderPost(id_user_name, gender, user_date_of_birth, premiumuser, String.valueOf(page))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(List<PostsModel> response) throws Exception {

                        getMvpView().getAgeGenderPosts(response);

                        // todo add data and loop to get all friends list
                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */

                        //    getMvpView().hideLoading();
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
    public void getAgeGenderPost02(String id_user_name, String gender, String user_date_of_birth, String premiumuser, int page) {

        //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getAgeGenderPost(id_user_name, gender, user_date_of_birth, premiumuser, String.valueOf(page))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(List<PostsModel> response) throws Exception {

                        getMvpView().getAgeGenderPosts02(response);

                        // todo add data and loop to get all friends list
                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */



                        //    getMvpView().hideLoading();
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
    public void getSearchPosts(String id_user_name, String search_word, String premium, int page) {

        //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getSearchPost(id_user_name, search_word, premium, String.valueOf(page))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(List<PostsModel> response) throws Exception {

                        getMvpView().getSearchPost(response);

                        // todo add data and loop to get all friends list
                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                        //    getMvpView().hideLoading();
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
    public void getSearchPosts02(String id_user_name, String search_word, String premium, int page) {

        //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getSearchPost(id_user_name, search_word, premium, String.valueOf(page))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(List<PostsModel> response) throws Exception {

                        getMvpView().getSearchPost02(response);

                        // todo add data and loop to get all friends list
                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                        //    getMvpView().hideLoading();
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
