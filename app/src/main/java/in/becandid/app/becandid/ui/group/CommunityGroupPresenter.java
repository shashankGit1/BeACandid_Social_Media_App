package in.becandid.app.becandid.ui.group;

import com.androidnetworking.error.ANError;

import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class CommunityGroupPresenter <V extends CommunityGroupMvpView> extends BasePresenter<V>
        implements CommunityGroupMvpPresenter<V> {

    @Inject
    public CommunityGroupPresenter(DataManager dataManager,
                                        SchedulerProvider schedulerProvider,
                                        CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getUserJoinedGroupsOnline(String user_id, String below18, String joined, int page) {
      //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getAllCommunityGroup(user_id, below18, joined, String.valueOf(page))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CommunityGroupPojoNew>>() {
                    @Override
                    public void accept(@NonNull List<CommunityGroupPojoNew> blogResponse)
                            throws Exception {
                        getMvpView().getUserJoinedGroups(blogResponse);

                     //   getMvpView().check_block_user(blogResponse);

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
    public void getUserJoinedGroupsOnline02(String user_id, String below18, String joined, int page) {
      //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getAllCommunityGroup(user_id, below18, joined, String.valueOf(page))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CommunityGroupPojoNew>>() {
                    @Override
                    public void accept(@NonNull List<CommunityGroupPojoNew> blogResponse)
                            throws Exception {
                        getMvpView().getUserJoinedGroups02(blogResponse);

                     //   getMvpView().check_block_user(blogResponse);

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
    public void sendJoinedGroupsOnline(String id_categories, String user_id) {
      //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .sendJoinGroup(id_categories, user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<InsertGroupPOJO>() {
                    @Override
                    public void accept(@NonNull InsertGroupPOJO blogResponse)
                            throws Exception {

                        getMvpView().sendJoinedGroups(blogResponse);

                      //  getMvpView().check_block_user(blogResponse);

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

}
