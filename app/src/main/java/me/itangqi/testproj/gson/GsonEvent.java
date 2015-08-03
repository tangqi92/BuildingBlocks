package me.itangqi.testproj.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rensong on 2014/6/7.
 */
public class GsonEvent implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("details")
    public String details;
    @SerializedName("locaton")
    public String locaton;
    @SerializedName("picture")
    public String picture;
    @SerializedName("date")
    public String date;
    @SerializedName("yesCount")
    public int yesCount;
    @SerializedName("noCount")
    public int noCount;
    @SerializedName("maybeCount")
    public int maybeCount;
    @SerializedName("url")
    public int url;

    public GsonEvent(String name, String details, String locaton, String date) {
        this.name = name;
        this.details = details;
        this.locaton = locaton;
        this.date = date;
    }

    public GsonEvent() {

    }


}
