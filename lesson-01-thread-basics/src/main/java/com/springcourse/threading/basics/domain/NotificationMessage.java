package com.springcourse.threading.basics.domain;

public record NotificationMessage(String accountId, String body) {
    public String shortLabel() {
        return accountId + ": " + body;
    }
}
