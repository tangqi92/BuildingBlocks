package me.itangqi.testproj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author bxbxbai
 */
public class Avatar implements Parcelable {

    public static final String ID = "id";

    public static final String TEMPLATE = "template";

    @SerializedName(ID)
    private String id;

    @SerializedName(TEMPLATE)
    private String template;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.template);
    }

    public Avatar() {
    }

    private Avatar(Parcel in) {
        this.id = in.readString();
        this.template = in.readString();
    }

    public static final Parcelable.Creator<Avatar> CREATOR = new Parcelable.Creator<Avatar>() {
        public Avatar createFromParcel(Parcel source) {
            return new Avatar(source);
        }

        public Avatar[] newArray(int size) {
            return new Avatar[size];
        }
    };
}
