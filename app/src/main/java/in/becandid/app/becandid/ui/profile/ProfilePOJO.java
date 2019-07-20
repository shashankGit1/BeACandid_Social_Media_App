package in.becandid.app.becandid.ui.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harishpc on 1/31/2018.
 */

public class ProfilePOJO {
    @SerializedName("facebook_friends_count")
    @Expose
    private Integer facebookFriendsCount;
    @SerializedName("contacts_count")
    @Expose
    private Integer contactsCount;
    @SerializedName("posts_count")
    @Expose
    private Integer postsCount;
    @SerializedName("groups_count")
    @Expose
    private Integer groupsCount;
    @SerializedName("givenFacebook")
    @Expose
    private Boolean givenFacebook;
    @SerializedName("givenContact")
    @Expose
    private Boolean givenContact;
    @SerializedName("givenLocation")
    @Expose
    private Boolean givenLocation;

    public Integer getFacebookFriendsCount() {
        return facebookFriendsCount;
    }

    public Integer getContactsCount() {
        return contactsCount;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public Integer getGroupsCount() {
        return groupsCount;
    }

    public Boolean getGivenFacebook() {
        return givenFacebook;
    }

    public Boolean getGivenContact() {
        return givenContact;
    }

    public Boolean getGivenLocation() {
        return givenLocation;
    }
}
