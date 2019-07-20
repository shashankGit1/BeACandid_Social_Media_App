package in.becandid.app.becandid.ui.discover;

import java.util.List;

import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.MvpView;

public interface DiscoverLatestMvpView extends MvpView {

    void getLatestPosts(List<PostsModel> postsModels);
    void getLatestPosts02(List<PostsModel> postsModels);






}
