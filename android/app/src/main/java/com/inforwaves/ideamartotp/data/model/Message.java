package com.inforwaves.ideamartotp.data.model;

public class Message {
    private String statusDetail;
    private String statusCode;
    private String data;

    public Message(String statusDetail, String statusCode) {
        this.statusDetail = statusDetail;
        this.statusCode = statusCode;
    }

    public Message(String statusDetail, String statusCode, String data) {
        this.statusDetail = statusDetail;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getData() {
        return data;
    }
}
