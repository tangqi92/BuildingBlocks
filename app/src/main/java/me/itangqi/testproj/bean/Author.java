package me.itangqi.testproj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Author
 * @author bxbxbai
 */
public class Author implements Parcelable {

    public static final String BIO = "bio";

    public static final String HASH = "hash";

    public static final String DESCRIPTION = "description";

    public static final String PROFILE_URL = "profileUrl";

    public static final String AVATAR = "avatar";

    public static final String SLUG = "slug";

    public static final String NAME = "name";

    @SerializedName(BIO)
    private String bio;

    @SerializedName(HASH)
    private String hash;

    @SerializedName(DESCRIPTION)
    private String description;

    @SerializedName(PROFILE_URL)
    private String profileUrl;

    @SerializedName(AVATAR)
    private Avatar avatar;

    @SerializedName(SLUG)
    private String slug;

    @SerializedName(NAME)
    private String name;


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bio);
        dest.writeString(this.hash);
        dest.writeString(this.description);
        dest.writeString(this.profileUrl);
        dest.writeParcelable(this.avatar, flags);
        dest.writeString(this.slug);
        dest.writeString(this.name);
    }

    public Author() {
    }

    private Author(Parcel in) {
        this.bio = in.readString();
        this.hash = in.readString();
        this.description = in.readString();
        this.profileUrl = in.readString();
        this.avatar = in.readParcelable(Avatar.class.getClassLoader());
        this.slug = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>() {
        public Author createFromParcel(Parcel source) {
            return new Author(source);
        }

        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
