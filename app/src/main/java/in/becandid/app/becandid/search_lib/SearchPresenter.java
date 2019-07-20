package in.becandid.app.becandid.search_lib;

import com.androidnetworking.error.ANError;

import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.ui.group.CommunityGroupPojo;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class SearchPresenter <V extends SearchMvpView> extends BasePresenter<V>
        implements SearchMvpPresenter<V> {

    @Inject
    public SearchPresenter(DataManager dataManager,
                                SchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getSearchGroup(String search_word, String user_id) {
      // getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getSearchGroup(search_word, user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<CommunityGroupPojo>>() {
                    @Override
                    public void accept(@NonNull List<CommunityGroupPojo> blogResponse)
                            throws Exception {

                        getMvpView().getSearchGroup(blogResponse);

                      //  getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {


                        //getMvpView().hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}


