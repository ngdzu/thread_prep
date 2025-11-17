package com.springcourse.threading.sharedstate.service;

import com.springcourse.threading.sharedstate.domain.SaleRequest;
import com.springcourse.threading.sharedstate.repository.InventoryLedger;

public final class InventoryTerminal implements Runnable {

    private final InventoryLedger ledger;
    private final SaleRequest saleRequest;
    private final boolean synchronizedAccess;
    private final int iterations;

    public InventoryTerminal(InventoryLedger ledger, SaleRequest saleRequest, boolean synchronizedAccess,
            int iterations) {
        this.ledger = ledger;
        this.saleRequest = saleRequest;
        this.synchronizedAccess = synchronizedAccess;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            if (synchronizedAccess) {
                ledger.recordSaleSynchronized(saleRequest);
            } else {
                ledger.recordSaleUnsafe(saleRequest);
            }
        }
    }
}
