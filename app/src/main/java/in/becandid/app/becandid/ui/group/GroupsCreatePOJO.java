package in.becandid.app.becandid.ui.group;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harishpc on 1/20/2018.
 */

public class GroupsCreatePOJO {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("category_id")
    @Expose
    private Integer groupId;
    @SerializedName("name")
    @Expose
    private String name;

    public Boolean getSuccess() {
        return success;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }
}
