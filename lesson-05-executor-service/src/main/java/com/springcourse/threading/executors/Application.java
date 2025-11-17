package com.springcourse.threading.executors;

import com.springcourse.threading.executors.controller.ThumbnailController;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) {
        ThumbnailController controller = new ThumbnailController();
        controller.generateThumbnails();
    }
}
