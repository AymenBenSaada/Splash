package com.example.macbook.splash.Models;

/**
 * Created by Master on 18/03/2018.
 */

public class ChildAdoptionResponseSubmitViewModel {

    private int requestId;

    private boolean accepted;

    public ChildAdoptionResponseSubmitViewModel(int requestId, boolean accepted) {
        this.requestId = requestId;
        this.accepted = accepted;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
