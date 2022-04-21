package com.code.fypurduvoiceassistant;

public class Upload {

    public Upload(String mEmail, String mImageUrl,String mFirstName,String mLastName) {
        this.mEmail = mEmail;
        this.mImageUrl = mImageUrl;
        this.mFirstName=mFirstName;
        this.mLastName=mLastName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    private String mEmail;
    private String mImageUrl;

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    private String mFirstName;
    private String mLastName;




}
