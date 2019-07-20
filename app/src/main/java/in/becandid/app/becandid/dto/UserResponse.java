package in.becandid.app.becandid.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harish on 1/3/2017.
 */

public class UserResponse {

    @SerializedName("status") @Expose private String status;
    @SerializedName("msg") @Expose private String msg;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
