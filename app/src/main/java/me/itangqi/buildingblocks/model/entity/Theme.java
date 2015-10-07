package me.itangqi.buildingblocks.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tangqi on 9/14/15.
 */
public class Theme implements Serializable {
    @SerializedName("subscribed")
    public boolean subscribed;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
}
