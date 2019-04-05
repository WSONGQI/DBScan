package wusongqi.dbscan.model;

import java.sql.Timestamp;

/**
 * Freemarker模板,由代码生成器生成
 * @author CHUCK
 * 
 */
public class Blog {

    /********** attribute ***********/
     /**blog_id*/
     private Integer blogId;
     /**user_id*/
     private Integer userId;
     /**text*/
     private String text;
     /**photo_url*/
     private String photoUrl;
     /**video_url*/
     private String videoUrl;
     /**music_url*/
     private String musicUrl;
     /**send_date*/
     private Timestamp sendDate;


    /********** constructors ***********/
    public Blog(){}

    public Blog(Integer blogId, Integer userId, String text, String photoUrl, String videoUrl, String musicUrl, Timestamp sendDate){
         this.blogId = blogId;
         this.userId = userId;
         this.text = text;
         this.photoUrl = photoUrl;
         this.videoUrl = videoUrl;
         this.musicUrl = musicUrl;
         this.sendDate = sendDate;
    }


    /********** get/set ***********/

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }



}
