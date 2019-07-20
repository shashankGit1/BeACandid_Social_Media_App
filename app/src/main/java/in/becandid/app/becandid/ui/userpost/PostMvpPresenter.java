package in.becandid.app.becandid.ui.userpost;

import java.io.File;

import in.becandid.app.becandid.di.PerActivity;
import in.becandid.app.becandid.ui.base.MvpPresenter;

@PerActivity
public interface PostMvpPresenter <V extends PostMvpView> extends MvpPresenter<V> {
    void getJoinedGroups(String user_id);
    void updatePost(String category, String action, String text_status);

    void reportAbuse(String id_user_name, String sender_user_id,String id_posts, String token, String message);

    void postImageUpload(File file);
    void postStatus(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter);
    void postStatusPending(String user_id, String post_text, String image_url, String group_id, String cat_id, String feeling_id, String type, String adult_filter);
}
