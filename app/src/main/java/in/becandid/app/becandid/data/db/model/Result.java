package in.becandid.app.becandid.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("message_id")
    @Expose
    private String messageId;

    public String getMessageId() {
        return messageId;
    }
}
