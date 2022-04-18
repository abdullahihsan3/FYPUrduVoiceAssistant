package com.code.fypurduvoiceassistant;

import android.graphics.drawable.Drawable;

public class ResponseClass {
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Drawable getPerson() {
        return person;
    }

    public void setPerson(Drawable person) {
        this.person = person;
    }

    public ResponseClass(String response, String time, Drawable person) {
        this.response = response;
        this.time = time;
        this.person = person;
    }

    String response;
    String time;
    Drawable person;

}
