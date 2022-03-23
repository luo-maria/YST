package com.example.yst.bean;

import android.net.Uri;

public class UriEvent {
    private String message;
    private Uri uri;

    public UriEvent(String message, Uri uri) {
        this.message = message;
        this.uri = uri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
