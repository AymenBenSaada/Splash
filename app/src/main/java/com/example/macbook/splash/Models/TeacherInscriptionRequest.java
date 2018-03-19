package com.example.macbook.splash.Models;

import java.util.Date;

/**
 * Created by Master on 18/03/2018.
 */

public class TeacherInscriptionRequest {

    private int id;

    private String header;

    private String content;

    private Date date;

    private String senderDescriptor;

    private String receiverDescriptor;

    public String sender;

    public String receiver;

    public int senderId;

    public int receiverId;

    public int childId;

    public Boolean handled;

    public TeacherInscriptionRequest(int id, String header, String content, Date date,
                                String senderDescriptor, String receiverDescriptor,
                                String sender, String receiver, int senderId,
                                int receiverId, int childId, Boolean handled) {
        this.id = id;
        this.header = header;
        this.content = content;
        this.date = date;
        this.senderDescriptor = senderDescriptor;
        this.receiverDescriptor = receiverDescriptor;
        this.sender = sender;
        this.receiver = receiver;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.childId = childId;
        this.handled = handled;
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

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public Boolean getHandled() {
        return handled;
    }

    public void setHandled(Boolean handled) {
        this.handled = handled;
    }
}
