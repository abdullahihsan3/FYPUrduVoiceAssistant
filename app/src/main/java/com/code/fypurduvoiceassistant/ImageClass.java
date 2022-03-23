package com.code.fypurduvoiceassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class ImageClass {
    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getCommand_name() {
        return command_name;
    }

    public void setCommand_name(String command_name) {
        this.command_name = command_name;
    }

    public String getCommand_short_description() {
        return command_short_description;
    }

    public void setCommand_short_description(String command_short_description) {
        this.command_short_description = command_short_description;
    }

    public String getCommand_sub_command_count() {
        return command_sub_command_count;
    }

    public void setCommand_sub_command_count(String command_sub_command_count) {
        this.command_sub_command_count = command_sub_command_count;
    }

    public ImageClass(Drawable image, String command_name, String command_short_description, String command_sub_command_count) {
        this.image = image;
        this.command_name = command_name;
        this.command_short_description = command_short_description;
        this.command_sub_command_count = command_sub_command_count;
    }

    Drawable image;
    String command_name;
    String command_short_description;
    String command_sub_command_count;





}
