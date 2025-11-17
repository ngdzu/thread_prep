package com.springcourse.threading.waitnotify.domain;

public record PickRequest(String orderId, String sku, int quantity) {
}
