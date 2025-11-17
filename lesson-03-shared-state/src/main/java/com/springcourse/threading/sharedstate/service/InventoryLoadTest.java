package com.springcourse.threading.sharedstate.service;

import com.springcourse.threading.sharedstate.domain.SaleRequest;
import com.springcourse.threading.sharedstate.repository.InventoryLedger;

import java.util.ArrayList;
import java.util.List;

public final class InventoryLoadTest {

    private final InventoryLedger ledger;
    private final int terminalCount;
    private final int salesPerTerminal;

    public InventoryLoadTest(InventoryLedger ledger, int terminalCount, int salesPerTerminal) {
        this.ledger = ledger;
        this.terminalCount = terminalCount;
        this.salesPerTerminal = salesPerTerminal;
    }

    public void execute() throws InterruptedException {
        int initial = ledger.snapshot().values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        List<Thread> threads = new ArrayList<>();
        SaleRequest sale = new SaleRequest("sku-100", 1);
        for (int i = 0; i < terminalCount; i++) {
            InventoryTerminal terminal = new InventoryTerminal(ledger, sale, true, salesPerTerminal);
            threads.add(new Thread(terminal, "load-terminal-" + (i + 1)));
        }

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        int finalQuantity = ledger.snapshot().values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        int expected = initial - (terminalCount * salesPerTerminal);

        System.out.printf("[load-test] Final inventory: %s%n", ledger.snapshot());
        assert finalQuantity == expected : "Synchronized ledger should reach the deterministic target";
    }
}
