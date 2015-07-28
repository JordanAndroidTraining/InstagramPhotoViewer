package com.example.jordanhsu.instagramphotoviewer;

/**
 * Created by jordanhsu on 7/29/15.
 */
public class CommentRow {
    private String commentUserProfileUrl;
    private String commentUserName;
    private String commentContent;

    public String getCommentUserProfileUrl() {
        return commentUserProfileUrl;
    }

    public void setCommentUserProfileUrl(String commentUserProfileUrl) {
        this.commentUserProfileUrl = commentUserProfileUrl;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
