package in.becandid.app.becandid.ui.userpost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostPojo {
    @SerializedName("adult")
    @Expose
    private Integer adult;
    @SerializedName("medical")
    @Expose
    private Integer medical;
    @SerializedName("spoof")
    @Expose
    private Integer spoof;
    @SerializedName("violence")
    @Expose
    private Integer violence;
    @SerializedName("racy")
    @Expose
    private Integer racy;
    @SerializedName("link")
    @Expose
    private String link;
}
