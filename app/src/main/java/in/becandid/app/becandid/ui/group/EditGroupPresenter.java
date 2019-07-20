package in.becandid.app.becandid.ui.group;

import com.androidnetworking.error.ANError;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import in.becandid.app.becandid.data.DataManager;
import in.becandid.app.becandid.dto.ImageUrl;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.ui.base.BasePresenter;
import in.becandid.app.becandid.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class EditGroupPresenter<V extends EditGroupMvpView> extends BasePresenter<V>
        implements EditGroupMvpPresenter<V> {

    @Inject
    public EditGroupPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
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

    @Override
    public void editGroup(String id_categories, String name, String group_description, String group_id) {

        getCompositeDisposable().add(getDataManager()
                .editGroup(id_categories, name,group_description, group_id )
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(SuccessResponse response) throws Exception {

                        getMvpView().editGroupSuccess(response);

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
    public void editGroupWithImage(String id_categories, String name, String group_image_url, String group_description, String group_id) {

        getCompositeDisposable().add(getDataManager()
                .editGroup(id_categories, name, group_image_url,group_description, group_id )
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<SuccessResponse>() {
                    @Override
                    public void accept(SuccessResponse response) throws Exception {

                        getMvpView().editGroupSuccess(response);

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
    public void postImageUpload(File file) {
        //       getMvpView().showLoading();

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


                        if (!isViewAttached()) {
                            return;
                        }

                        //         getMvpView().hideLoading();
                        //   getMvpView().openMainActivity();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                        //  getMvpView().hideLoading();

                        // handle the login error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}
