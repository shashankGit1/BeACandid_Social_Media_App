package in.becandid.app.becandid.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by harish on 2/26/2017.
 */

public class CategoryJoined {

    @SerializedName("id_categories") @Expose private int id_categories;

    public int getId_categories() {
        return id_categories;
    }



}
