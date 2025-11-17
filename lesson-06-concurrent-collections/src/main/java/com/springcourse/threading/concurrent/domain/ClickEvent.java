package com.springcourse.threading.concurrent.domain;

public record ClickEvent(String campaignId, String country, long dwellMillis) {
}
