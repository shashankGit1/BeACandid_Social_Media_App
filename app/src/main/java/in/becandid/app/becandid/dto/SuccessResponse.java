package in.becandid.app.becandid.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harish on 2/8/2017.
 */

public class SuccessResponse {


    @SerializedName("success") @Expose private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

}
