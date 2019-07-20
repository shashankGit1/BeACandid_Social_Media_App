package in.becandid.app.becandid.ui.discover;

import java.util.List;

import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.MvpView;

public interface DiscoverFacebookMvpView extends MvpView {

    void getFacebookPosts(List<PostsModel> response);
    void getFacebookPosts02(List<PostsModel> response);


}
