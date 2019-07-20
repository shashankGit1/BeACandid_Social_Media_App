package in.becandid.app.becandid.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 6/12/17.
 */

public class ProfileMain {
    @SerializedName("posts_count")
    @Expose
    private Integer postsCount;
    @SerializedName("groups_count")
    @Expose
    private Integer groupsCount;
    @SerializedName("givenFacebook")
    @Expose
    private Boolean givenFacebook;

    @SerializedName("user_nick_name")
    @Expose
    private String user_nick_name;



    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public Integer getGroupsCount() {
        return groupsCount;
    }

    public void setGroupsCount(Integer groupsCount) {
        this.groupsCount = groupsCount;
    }

    public Boolean getGivenFacebook() {
        return givenFacebook;
    }

    public void setGivenFacebook(Boolean givenFacebook) {
        this.givenFacebook = givenFacebook;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }
}
