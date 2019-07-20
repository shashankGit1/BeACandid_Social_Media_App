package in.becandid.app.becandid.ui.group;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harishpc on 11/24/2017.
 */

public class JoinGroupResponse {
    @SerializedName("status") @Expose private Integer status;
    @SerializedName("success") @Expose private Boolean success;
    @SerializedName("count_groups_requested") @Expose private Integer countGroupsRequested;
    @SerializedName("count_groups_joined") @Expose private Integer countGroupsJoined;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCountGroupsRequested() {
        return countGroupsRequested;
    }

    public Integer getCountGroupsJoined() {
        return countGroupsJoined;
    }
}
