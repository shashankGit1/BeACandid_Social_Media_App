package in.becandid.app.becandid.ui.profile;

import com.androidnetworking.error.ANError;

import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class NotificationPresenter <V extends NotificationMvpView> extends BasePresenter<V>
        implements NotificationMvpPresenter<V> {

    @Inject
    public NotificationPresenter(DataManager dataManager,
                                    SchedulerProvider schedulerProvider,
                                    CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getNotificationOnline(String id_user_name, String page) {
        //   getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getNotificationData(id_user_name, page)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<NotificationPojo>>() {
                    @Override
                    public void accept(@NonNull List<NotificationPojo> blogResponse)
                            throws Exception {

                        getMvpView().getNotification(blogResponse);

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
