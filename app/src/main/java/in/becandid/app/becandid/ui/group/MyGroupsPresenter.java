package in.becandid.app.becandid.ui.group;

import com.androidnetworking.error.ANError;

import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MyGroupsPresenter <V extends MyGroupsMvpView> extends BasePresenter<V>
        implements MyGroupsMvpPresenter<V> {

    @Inject
    public MyGroupsPresenter(DataManager dataManager,
                              SchedulerProvider schedulerProvider,
                              CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getListUserJoinedGroups01(String user_id, String below18, String joined, String page) {
      //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getAllCommunityGroup(user_id, below18, joined, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CommunityGroupPojoNew>>() {
                    @Override
                    public void accept(List<CommunityGroupPojoNew> response) throws Exception {

                        getMvpView().getListUserJoinedGroups01(response);

                     /*   getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */



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
    public void getListUserJoinedGroups02(String user_id, String below18, String joined, String page) {
       // getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getAllCommunityGroup(user_id, below18, joined, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CommunityGroupPojoNew>>() {
                    @Override
                    public void accept(List<CommunityGroupPojoNew> response) throws Exception {

                        getMvpView().getListUserJoinedGroups02(response);

                     /*   getDataManager().updateUserInfo(

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
    public void getListAllGroupsOnline(String userID) {
        //   getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getListAllGroups(userID)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<GroupUser>>() {
                    @Override
                    public void accept(List<GroupUser> response) throws Exception {

                        getMvpView().getListAllGroups(response);

                     /*   getDataManager().updateUserInfo(

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
