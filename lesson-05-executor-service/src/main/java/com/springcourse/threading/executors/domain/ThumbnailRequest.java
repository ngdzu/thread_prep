package com.springcourse.threading.executors.domain;

public record ThumbnailRequest(String campaignId, String imagePath, int targetWidth) {
}
