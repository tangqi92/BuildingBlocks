package me.itangqi.buildingblocks.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangqi on 8/20/15.
 */
public class DailyHttpResult implements Serializable {
    @SerializedName("date")
    public String date;
    @SerializedName("stories")
    public List<DailyHttp> stories;
}
