package in.becandid.app.becandid.ui.discover;

import com.androidnetworking.error.ANError;

import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class DiscoverFacebookPresenter <V extends DiscoverFacebookMvpView> extends BasePresenter<V>
        implements DiscoverFacebookMvpPresenter<V> {

    @Inject
    public DiscoverFacebookPresenter(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getFacebookPostsOnline(String id_user_name, String user_id, String facebookId, String page) {

        //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getFacebookFriendsFeed(id_user_name, user_id, facebookId, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(List<PostsModel> response) throws Exception {

                        getMvpView().getFacebookPosts(response);
                        // todo add data and loop to get all friends list
                   /*     getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */

                        //   getMvpView().hideLoading();
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
    public void getFacebookPostsOnline02(String id_user_name, String user_id, String facebookId, String page) {
        //   getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .getFacebookFriendsFeed(id_user_name, user_id, facebookId, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(List<PostsModel> response) throws Exception {

                        getMvpView().getFacebookPosts02(response);
                        // todo add data and loop to get all friends list
                   /*     getDataManager().updateUserInfo(

                                response.info.getId(),
                                response.info.getUser_token(),
                                DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER
                        );
                        */


                        //   getMvpView().hideLoading();
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
}
