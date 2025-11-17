package com.springcourse.threading.sharedstate;

import com.springcourse.threading.sharedstate.controller.InventoryDemoController;
import com.springcourse.threading.sharedstate.repository.InventoryLedger;
import com.springcourse.threading.sharedstate.service.InventoryLoadTest;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) throws InterruptedException {
        InventoryDemoController controller = new InventoryDemoController();
        controller.runUnsafeScenario();
        controller.runSynchronizedScenario();

        InventoryLedger ledger = InventoryLedger.createWithSku("sku-100", 120);
        InventoryLoadTest loadTest = new InventoryLoadTest(ledger, 4, 20);
        loadTest.execute();
    }
}
