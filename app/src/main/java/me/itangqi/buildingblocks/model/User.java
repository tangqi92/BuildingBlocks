package me.itangqi.buildingblocks.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author bxbxbai
 */
public class User {

    @SerializedName("followersCount")
    private int followerCount;

    @SerializedName("description")
    private String description;

    @SerializedName("creator")
    private Author author;

    @SerializedName("topics")
    private List<Topic> topics;

    @SerializedName("href")
    private String href;

    @SerializedName("acceptSubmission")
    private boolean acceptSubmission;

    @SerializedName("slug")
    private String slug;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("avatar")
    private Avatar avatar;

    @SerializedName("commentPermission")
    private String commentPermission;

    @SerializedName("following")
    private boolean following;

    @SerializedName("postsCount")
    private int postCount;

    @SerializedName("canPost")
    private boolean canPost;

    @SerializedName("activateAuthorRequested")
    private boolean activateAuthorRequested;

    public int getFollowerCount() {
        return followerCount;
    }

    public String getDescription() {
        return description;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public String getHref() {
        return href;
    }

    public boolean isAcceptSubmission() {
        return acceptSubmission;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getCommentPermission() {
        return commentPermission;
    }

    public boolean isFollowing() {
        return following;
    }

    public int getPostCount() {
        return postCount;
    }

    public boolean isCanPost() {
        return canPost;
    }

    public boolean isActivateAuthorRequested() {
        return activateAuthorRequested;
    }

    public static class Topic {
        @SerializedName("url")
        private String url;

        @SerializedName("id")
        private String id;

        @SerializedName("name")
        private String name;

        public String getUrl() {
            return url;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
