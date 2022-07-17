package com.apps.albayt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusResponse implements Serializable {
    @SerializedName(value = "status",alternate = {"code"})
    protected int status;
    protected Object msg;

    public int getStatus() {
        return status;
    }

    public Object getMessage() {
        return msg;
    }
}
