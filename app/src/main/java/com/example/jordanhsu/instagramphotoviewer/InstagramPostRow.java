package com.example.jordanhsu.instagramphotoviewer;

/**
 * Created by jordanhsu on 7/26/15.
 */
public class InstagramPostRow {
    private String userName;
    private String caption;
    private String likeCount;
    private String mainPhotoUrl;
    private String userProfilePhotoUrl;
    private String commentUserName1;
    private String commentContent1;
    private String commentUserProfilePhotoUrl1;
    private String commentUserName2;
    private String commentContent2;
    private String commentUserProfilePhotoUrl2;
    private Long createTimestamp;
    private String relativeTimestamp;

    public String getRelativeTimestamp() {
        return relativeTimestamp;
    }

    public void setRelativeTimestamp(String relativeTimestamp) {
        this.relativeTimestamp = relativeTimestamp;
    }

    public Long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }


    public String getCommentUserProfilePhotoUrl1() {
        return commentUserProfilePhotoUrl1;
    }

    public void setCommentUserProfilePhotoUrl1(String commentUserProfilePhotoUrl1) {
        this.commentUserProfilePhotoUrl1 = commentUserProfilePhotoUrl1;
    }

    public String getCommentUserProfilePhotoUrl2() {
        return commentUserProfilePhotoUrl2;
    }

    public void setCommentUserProfilePhotoUrl2(String commentUserProfilePhotoUrl2) {
        this.commentUserProfilePhotoUrl2 = commentUserProfilePhotoUrl2;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setMainPhotoUrl(String mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public String getUserProfilePhotoUrl() {
        return userProfilePhotoUrl;
    }

    public void setUserProfilePhotoUrl(String userProfilePhotoUrl) {
        this.userProfilePhotoUrl = userProfilePhotoUrl;
    }

    public String getCommentUserName1() {
        return commentUserName1;
    }

    public void setCommentUserName1(String commentUserName1) {
        this.commentUserName1 = commentUserName1;
    }

    public String getCommentContent1() {
        return commentContent1;
    }

    public void setCommentContent1(String commentContent1) {
        this.commentContent1 = commentContent1;
    }

    public String getCommentUserName2() {
        return commentUserName2;
    }

    public void setCommentUserName2(String commentUserName2) {
        this.commentUserName2 = commentUserName2;
    }

    public String getCommentContent2() {
        return commentContent2;
    }

    public void setCommentContent2(String commentContent2) {
        this.commentContent2 = commentContent2;
    }
}
