package com.springcourse.threading.sharedstate.controller;

import com.springcourse.threading.sharedstate.domain.SaleRequest;
import com.springcourse.threading.sharedstate.repository.InventoryLedger;
import com.springcourse.threading.sharedstate.service.InventoryTerminal;

import java.util.ArrayList;
import java.util.List;

public final class InventoryDemoController {

    private static final String SKU = "sku-100";
    private static final int INITIAL_QUANTITY = 120;
    private static final int TERMINALS = 4;
    private static final int SALES_PER_TERMINAL = 20;

    public void runUnsafeScenario() throws InterruptedException {
        InventoryLedger ledger = InventoryLedger.createWithSku(SKU, INITIAL_QUANTITY);
        System.out.println("--- Running UNSAFE inventory update ---");
        executeScenario(ledger, false);
        int remaining = ledger.remainingUnsafe(SKU);
        System.out.printf("Remaining units (unsafe): %d%n", remaining);
        assert remaining != expectedRemaining() : "Unsafe run should reveal a race";
    }

    public void runSynchronizedScenario() throws InterruptedException {
        InventoryLedger ledger = InventoryLedger.createWithSku(SKU, INITIAL_QUANTITY);
        System.out.println("--- Running SYNCHRONIZED inventory update ---");
        executeScenario(ledger, true);
        int remaining = ledger.remaining(SKU);
        System.out.printf("Remaining units (synchronized): %d%n", remaining);
        assert remaining == expectedRemaining() : "Synchronized run should preserve the count";
    }

    private void executeScenario(InventoryLedger ledger, boolean synchronizedAccess) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        SaleRequest sale = new SaleRequest(SKU, 1);
        for (int i = 0; i < TERMINALS; i++) {
            InventoryTerminal terminal = new InventoryTerminal(ledger, sale, synchronizedAccess, SALES_PER_TERMINAL);
            Thread thread = new Thread(terminal, "terminal-" + (i + 1));
            threads.add(thread);
        }

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private int expectedRemaining() {
        return INITIAL_QUANTITY - (TERMINALS * SALES_PER_TERMINAL);
    }
}
