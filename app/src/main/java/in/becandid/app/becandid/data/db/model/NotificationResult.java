package in.becandid.app.becandid.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResult {
    @SerializedName("multicast_id")
    @Expose
    private Integer multicastId;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("failure")
    @Expose
    private Integer failure;
    @SerializedName("canonical_ids")
    @Expose
    private Integer canonicalIds;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Integer getMulticastId() {
        return multicastId;
    }

    public Integer getSuccess() {
        return success;
    }

    public Integer getFailure() {
        return failure;
    }

    public Integer getCanonicalIds() {
        return canonicalIds;
    }

    public List<Result> getResults() {
        return results;
    }
}
