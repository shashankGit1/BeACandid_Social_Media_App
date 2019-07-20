package in.becandid.app.becandid.ui.profile;

import java.util.List;

import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.MvpView;

public interface MyPostsMvpView extends MvpView {

    void getSingleUserPosts(List<PostsModel> postsModels);
    void getSingleUserPosts02(List<PostsModel> postsModels);
}
