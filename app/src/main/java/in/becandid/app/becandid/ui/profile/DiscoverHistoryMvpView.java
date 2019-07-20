package in.becandid.app.becandid.ui.profile;

import java.util.List;

import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.MvpView;

public interface DiscoverHistoryMvpView extends MvpView {

    void getHistoryPosts01(List<PostsModel> postsModels);
    void getHistoryPosts02(List<PostsModel> postsModels);

}
