package me.itangqi.buildingblocks.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangqi on 8/20/15.
 */
public class Daily implements Serializable {
    @SerializedName("images")
    public List<String> images;
    @SerializedName("image")
    public String image;
    @SerializedName("type")
    public int type;
    @SerializedName("id")
    public int id;
    @SerializedName("ga_prefix")
    public int ga_prefix;
    @SerializedName("title")
    public String title;
    @SerializedName("multipic")
    public boolean multipic;
    @SerializedName("theme")
    public Theme theme;
}
