package com.example.macbook.splash.Models;

/**
 * Created by Master on 18/03/2018.
 */

public class ChildAdoptionRequestSubmitViewModel {

    private int SenderId;

    private int ReceiverId;

    private int ChildId;

    public ChildAdoptionRequestSubmitViewModel(int senderId, int receiverId, int childId) {
        SenderId = senderId;
        ReceiverId = receiverId;
        ChildId = childId;
    }

    public int getSenderId() {
        return SenderId;
    }

    public void setSenderId(int senderId) {
        SenderId = senderId;
    }

    public int getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(int receiverId) {
        ReceiverId = receiverId;
    }

    public int getChildId() {
        return ChildId;
    }

    public void setChildId(int childId) {
        ChildId = childId;
    }

}
