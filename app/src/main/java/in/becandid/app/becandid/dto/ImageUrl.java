package in.becandid.app.becandid.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageUrl {
    @SerializedName("adult")
    @Expose
    private int adult;
    @SerializedName("medical")
    @Expose
    private int medical;
    @SerializedName("spoof")
    @Expose
    private int spoof;
    @SerializedName("violence")
    @Expose
    private int violence;
    @SerializedName("racy")
    @Expose
    private int racy;
    @SerializedName("link")
    @Expose
    private String link;

    public int getAdult() {
        return adult;
    }

    public int getMedical() {
        return medical;
    }

    public int getSpoof() {
        return spoof;
    }

    public int getViolence() {
        return violence;
    }

    public int getRacy() {
        return racy;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
