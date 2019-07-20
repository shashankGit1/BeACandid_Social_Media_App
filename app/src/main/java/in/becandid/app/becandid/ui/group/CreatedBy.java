package in.becandid.app.becandid.ui.group;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harishpc on 11/25/2017.
 */

public class CreatedBy {

    @SerializedName("id_user_name") @Expose private String idUserName;
    @SerializedName("user_nick_name") @Expose private String userNickName;


    public String getIdUserName() {
        return idUserName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public CreatedBy(String idUserName, String userNickName) {
        this.idUserName = idUserName;
        this.userNickName = userNickName;
    }
}
