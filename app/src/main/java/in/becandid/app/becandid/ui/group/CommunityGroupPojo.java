package in.becandid.app.becandid.ui.group;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harishpc on 11/25/2017.
 */

public class CommunityGroupPojo {


   @SerializedName("created_by_id_user_name") @Expose private String created_by_id_user_name;
    @SerializedName("is_joined") @Expose private Boolean isJoined;
    @SerializedName("created_at") @Expose private String createdAt;
    @SerializedName("joined_at") @Expose private String joinedAt;

    @SerializedName("group_id") @Expose private String groupId;
    @SerializedName("group_name") @Expose private String groupName;
    @SerializedName("group_notif") @Expose private boolean group_notif;
    @SerializedName("group_image_url") @Expose private String groupImageUrl;
    @SerializedName("group_description") @Expose private String groupDescription;
    @SerializedName("users_in_group") @Expose private String usersInGroup;
    @SerializedName("posts_inside_groups") @Expose private String postsInsideGroups;
    @SerializedName("id_categories") @Expose private String idCategories;
    @SerializedName("category_name") @Expose private String categoryName;
    @SerializedName("created_by") @Expose private CreatedBy createdBy;

    public boolean isGroup_notif() {
        return group_notif;
    }

    public String getCreated_by_id_user_name() {
        return created_by_id_user_name;
    }

    public Boolean getJoined() {
        return isJoined;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getJoinedAt() {
        return joinedAt;
    }


    public String getGroupImageUrl() {
        return groupImageUrl;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public String getUsersInGroup() {
        return usersInGroup;
    }

    public String getPostsInsideGroups() {
        return postsInsideGroups;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getIdCategories() {
        return idCategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    @Override
    public String toString() {
        return this.groupName;
    }
}
