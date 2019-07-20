package in.becandid.app.becandid.ui.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harishpc on 2/16/2018.
 */

public class locationPOJO {

    @SerializedName("lat")
    @Expose
    protected double latitude;
    @SerializedName("lng")
    @Expose
    protected double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
