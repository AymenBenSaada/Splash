package com.example.macbook.splash.Models;

/**
 * Created by nader on 3/17/18.
 */

public class TeacherInscriptionRequestSubmitViewModel {
    public int SenderId ;
    public int ReceiverId ;

    public TeacherInscriptionRequestSubmitViewModel(int senderId, int receiverId) {
        SenderId = senderId;
        ReceiverId = receiverId;
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
}
