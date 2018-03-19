package com.example.macbook.splash.Models;

import java.util.Date;

/**
 * Created by Master on 18/03/2018.
 */

public class ChildAdoptionResponse {

    private int id;

    private String header;

    private String content;

    private Boolean accepted;

    private Boolean viewed;

    private Date date;

    private String senderDescriptor;

    private String receiverDescriptor;

    private String sender;

    private String receiver;

    private int senderId;

    private int receiverId;

    private int requestId;

    public ChildAdoptionResponse(int id, String header, String content, Boolean accepted,
                                 Boolean viewed, Date date, String senderDescriptor,
                                 String receiverDescriptor, String sender, String receiver,
                                 int senderId, int receiverId, int requestId) {
        this.id = id;
        this.header = header;
        this.content = content;
        this.accepted = accepted;
        this.viewed = viewed;
        this.date = date;
        this.senderDescriptor = senderDescriptor;
        this.receiverDescriptor = receiverDescriptor;
        this.sender = sender;
        this.receiver = receiver;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.requestId = requestId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSenderDescriptor() {
        return senderDescriptor;
    }

    public void setSenderDescriptor(String senderDescriptor) {
        this.senderDescriptor = senderDescriptor;
    }

    public String getReceiverDescriptor() {
        return receiverDescriptor;
    }

    public void setReceiverDescriptor(String receiverDescriptor) {
        this.receiverDescriptor = receiverDescriptor;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
