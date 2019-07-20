package in.becandid.app.becandid.ui.profile;

import com.androidnetworking.error.ANError;

import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class DiscoverHistoryPresenter <V extends DiscoverHistoryMvpView> extends BasePresenter<V>
        implements DiscoverHistoryMvpPresenter<V> {

    @Inject
    public DiscoverHistoryPresenter(DataManager dataManager,
                                       SchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getHistoryPostsOnline(String id_user_name, String user_id, String group_post, int page) {
        //    getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getHistoryPosts(id_user_name, user_id, group_post,String.valueOf(page))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(@NonNull List<PostsModel> blogResponse)
                            throws Exception {

                        getMvpView().getHistoryPosts01(blogResponse);

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
    public void getHistoryPostsOnline02(String id_user_name, String user_id, String group_post, int page) {
        //  getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getHistoryPosts(id_user_name, user_id, group_post,String.valueOf(page))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PostsModel>>() {
                    @Override
                    public void accept(@NonNull List<PostsModel> blogResponse)
                            throws Exception {

                        getMvpView().getHistoryPosts02(blogResponse);

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
}
