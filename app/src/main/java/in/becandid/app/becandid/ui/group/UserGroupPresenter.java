package in.becandid.app.becandid.ui.group;

import com.androidnetworking.error.ANError;

import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserGroupPresenter<V extends UserGroupMvpView> extends BasePresenter<V>
        implements UserGroupMvpPresenter<V> {

    @Inject
    public UserGroupPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getGroupPosts(String group_id, String user_id, String page) {
      //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getGroupPosts(group_id, user_id, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(List<PostsModel> response) throws Exception {

                        getMvpView().getGroupPosts(response);

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

    @Override
    public void getGroupPosts02(String group_id, String user_id, String page) {
      //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getGroupPosts(group_id, user_id, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(List<PostsModel> response) throws Exception {

                        getMvpView().getGroupPosts02(response);

                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */



                      //  getMvpView().hideLoading();
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

    @Override
    public void sendJoinGroup(String group_ids, String user_id) {
    //    getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .sendJoinGroup(group_ids, user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<InsertGroupPOJO>() {
                    @Override
                    public void accept(InsertGroupPOJO response) throws Exception {

                        getMvpView().sendJoinGroup(response);

                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */



                     //   getMvpView().hideLoading();
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

    @Override
    public void sendUnJoinGroup(String group_ids, String user_id) {
     //   getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .sendUnJoinGroup(group_ids, user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<InsertGroupPOJO>() {
                    @Override
                    public void accept(InsertGroupPOJO response) throws Exception {

                        getMvpView().sendUnJoinGroup(response);

                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */



                      //  getMvpView().hideLoading();
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

    @Override
    public void getGroupSpecific(String group_id, String user_id, String page) {
      //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getGroupSpecific(group_id, user_id, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CommunityGroupPojo>>() {
                    @Override
                    public void accept(List<CommunityGroupPojo> response) throws Exception {

                        getMvpView().getGroupSpecific(response);

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


