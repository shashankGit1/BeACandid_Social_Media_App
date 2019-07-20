package in.becandid.app.becandid.ui.discover;

import java.util.List;

import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.MvpView;

public interface DiscoverTrendingMvpView extends MvpView {

    void getTrendingPosts(List<PostsModel> response);
    void getImagePosts(List<PostsModel> response);
    void getPendingPosts(List<PostsModel> response);
    void getPendingPosts02(List<PostsModel> response);
    void getTrendingPosts02(List<PostsModel> response);
    void getImagePosts02(List<PostsModel> response);
}
