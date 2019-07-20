package in.becandid.app.becandid.ui.group;

import com.androidnetworking.error.ANError;

import java.io.File;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.ImageUrl;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class CreateGroupTagPresenter<V extends CreateGroupTagMvpView> extends BasePresenter<V>
        implements CreateGroupTagMvpPresenter<V> {

    @Inject
    public CreateGroupTagPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void postNewCategoryOnline(String id_categories, String user_id, String group_name, String group_image, String group_description) {
        // getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .postInsertGroup(id_categories, user_id, group_name, group_image, group_description)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<GroupsCreatePOJO>() {
                    @Override
                    public void accept(GroupsCreatePOJO response) throws Exception {

                        getMvpView().postNewCategory(response);

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
    public void postImageUpload(File file) {
        //  getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .postImageUpload(file)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ImageUrl>() {
                    @Override
                    public void accept(ImageUrl response) throws Exception {

                        getMvpView().getImageUrl(response.getLink());
                        //   getMvpView().getUserJoinedGroupsFirstPage(response);

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
}
