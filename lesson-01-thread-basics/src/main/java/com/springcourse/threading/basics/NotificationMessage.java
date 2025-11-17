package com.springcourse.threading.basics;

public record NotificationMessage(String accountId, String body) {
    public String shortLabel() {
        return accountId + ": " + body;
    }
}
