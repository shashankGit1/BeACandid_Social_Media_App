package in.becandid.app.becandid.ui.filter;

import java.util.List;

import in.becandid.app.becandid.dto.PostsModel;
import in.becandid.app.becandid.ui.base.MvpView;

public interface PremiumSearchResultMvpView extends MvpView {

    void getAgeGenderPosts(List<PostsModel> postsModels);
    void getAgeGenderPosts02(List<PostsModel> postsModels);

    void getSearchPost(List<PostsModel> postsModels);
    void getSearchPost02(List<PostsModel> postsModels);


}
