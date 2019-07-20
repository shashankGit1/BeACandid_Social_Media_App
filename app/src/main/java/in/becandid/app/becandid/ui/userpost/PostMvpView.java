package in.becandid.app.becandid.ui.userpost;

import java.util.List;

import in.becandid.app.becandid.autocomplete.GroupUser;
import in.becandid.app.becandid.dto.ImageUrl;
import in.becandid.app.becandid.dto.SuccessResponse;
import in.becandid.app.becandid.dto.UserResponse;
import in.becandid.app.becandid.ui.base.MvpView;

public interface PostMvpView extends MvpView {

    void getJoinedGroups(List<GroupUser> groupUsers);
    void postStatus(UserResponse groupUsers);
    void getImageUrl(ImageUrl image);

    void updatePost(SuccessResponse successResponse);
    void reportAbuse(SuccessResponse successResponse);


}
