package com.example.fangyuanzheng.schoolday;

/**
 * Created by Fangyuan Zheng on 4/11/2017.
 */
import java.io.Serializable;
public class Classes implements Serializable {
    String name;
    String starttime;
    String endtime;

    public String getClasslocation() {
        return classlocation;
    }

    public void setClasslocation(String classlocation) {
        this.classlocation = classlocation;
    }

    String classlocation;
    String id;
    String primaryID;
    public Classes (){

    }

    public String getClassName() {
        return name;
    }

    public void setClassName(String className) {
        this.name = className;
    }

    public String getClassStartTime() {
        return starttime;
    }

    public void setClassStartTime(String classStartTime) {
        this.starttime = classStartTime;
    }

    public String getClassEndTime() {
        return endtime;
    }

    public String getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(String primaryID) {
        this.primaryID = primaryID;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClassEndTime(String classEndTime) {
        this.endtime = classEndTime;
    }


}
