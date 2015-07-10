package me.itangqi.testproj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author bxbxbai
 */
public class Post implements Parcelable {

    @SerializedName("rating")
    private String rating;

    @SerializedName("sourceUrl")
    private String sourceUrl;

    @SerializedName("publishedTime")
    private String publishedTime;

    @SerializedName("links")
    private Comment comment;

    @SerializedName("author")
    private Author author;

    @SerializedName("column")
    private Column column;

    @SerializedName("title")
    private String title;

    @SerializedName("titleImage")
    private String titleImage;

    @SerializedName("summary")
    private String summary;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    @SerializedName("state")
    private String state;

    @SerializedName("href")
    private String href;

    @SerializedName("commentsCount")
    private int commentsCount;

    @SerializedName("likesCount")
    private int likesCount;

    public String getRating() {
        return rating;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public Comment getComment() {
        return comment;
    }

    public Author getAuthor() {
        return author;
    }

    public Column getColumn() {
        return column;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public String getState() {
        return state;
    }

    public String getHref() {
        return href;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public class Comment {
        @SerializedName("comments")
        String comments;
    }

    public class Column {
        @SerializedName("slug")
        String slug;

        @SerializedName("name")
        String name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rating);
        dest.writeString(this.sourceUrl);
        dest.writeString(this.publishedTime);
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.title);
        dest.writeString(this.titleImage);
        dest.writeString(this.summary);
        dest.writeString(this.content);
        dest.writeString(this.url);
        dest.writeString(this.state);
        dest.writeString(this.href);
        dest.writeInt(this.commentsCount);
        dest.writeInt(this.likesCount);
    }

    public Post() {
    }

    private Post(Parcel in) {
        this.rating = in.readString();
        this.sourceUrl = in.readString();
        this.publishedTime = in.readString();
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.title = in.readString();
        this.titleImage = in.readString();
        this.summary = in.readString();
        this.content = in.readString();
        this.url = in.readString();
        this.state = in.readString();
        this.href = in.readString();
        this.commentsCount = in.readInt();
        this.likesCount = in.readInt();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
