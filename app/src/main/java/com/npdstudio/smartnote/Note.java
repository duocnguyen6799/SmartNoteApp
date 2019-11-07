package com.npdstudio.smartnote;

import java.io.Serializable;

public class Note implements Serializable {
    int mID;

    String mTitle;

    String mContent;
    String mTime;
    String mTag;
    boolean isChecked;

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }



    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Note(String mTitle) {
        this.mTitle = mTitle;
    }

    public Note() {
    }

    public Note(int mID, String mTitle, String mTag, String mContent, String mTime) {
        this.mID = mID;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mTime = mTime;
        this.mTag = mTag;
    }

    public Note(String mTitle, String mTag, String mContent, String mTime) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mTime = mTime;
        this.mTag = mTag;
    }

    public Note(String mTitle, String mTag, String mContent, String mTime, boolean isChecked) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mTime = mTime;
        this.mTag = mTag;
        this.isChecked = isChecked;
    }
}
