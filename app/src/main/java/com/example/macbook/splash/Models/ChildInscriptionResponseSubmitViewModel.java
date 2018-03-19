package com.example.macbook.splash.Models;

/**
 * Created by Master on 18/03/2018.
 */

public class ChildInscriptionResponseSubmitViewModel {

    private int requestId;

    private int groupId;

    private boolean accepted;

    public ChildInscriptionResponseSubmitViewModel(int requestId, int groupId, boolean accepted) {
        this.requestId = requestId;
        this.groupId = groupId;
        this.accepted = accepted;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
