package com.example.fangyuanzheng.schoolday;

/**
 * Created by Fangyuan Zheng on 4/16/2017.
 */
import java.io.Serializable;
public class Notes implements Serializable  {
    String content;
    String date;
    String id;
    String name;
    String year;
    String video;
    String image;
    String primaryID;

    public String getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(String primaryID) {
        this.primaryID = primaryID;
    }

    public Notes() {

    }

    public String getNoteContent() {
        return content;
    }

    public void setNoteContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {

        return video;
    }

    public void setVideo(String video) {
        video = video;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {

        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
