package in.becandid.app.becandid.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harish on 2/8/2017.
 */

public class SuccessResponseChat {


    @SerializedName("success") @Expose private Boolean success;
    @SerializedName("blocked") @Expose private Boolean blocked;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getBlocked() {
        return blocked;
    }
}
