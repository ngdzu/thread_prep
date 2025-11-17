package com.springcourse.threading.executors.domain;

import java.time.Duration;

public record Thumbnail(String campaignId, String imagePath, Duration renderDuration) {
}
