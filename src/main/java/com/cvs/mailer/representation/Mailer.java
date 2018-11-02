package com.cvs.mailer.representation;

import io.swagger.annotations.ApiModelProperty;

public class Mailer {

    @ApiModelProperty(notes = "The printed application ID")
    private final long id;

    @ApiModelProperty(notes = "The recipient's email ID")
    private String toAddress;

    @ApiModelProperty(notes = "The sender's email subject")
    private String subject;

    @ApiModelProperty(notes = "The sender's email content in HTML format")
    private String body;

    public Mailer(long id, String toAddress, String subject, String body) {
        this.id = id;
        this.toAddress = toAddress;
        this.subject = subject;
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
