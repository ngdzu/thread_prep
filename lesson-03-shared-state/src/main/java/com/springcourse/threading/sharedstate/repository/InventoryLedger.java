package com.springcourse.threading.sharedstate.repository;

import com.springcourse.threading.sharedstate.domain.SaleRequest;

import java.util.HashMap;
import java.util.Map;

public final class InventoryLedger {

    private final Map<String, Integer> stockLevels = new HashMap<>();
    private final Object lock = new Object();

    private InventoryLedger() {
    }

    public static InventoryLedger createWithSku(String sku, int initialQuantity) {
        InventoryLedger ledger = new InventoryLedger();
        ledger.stockLevels.put(sku, initialQuantity);
        return ledger;
    }

    public void recordSaleUnsafe(SaleRequest sale) {
        int remaining = stockLevels.getOrDefault(sale.sku(), 0) - sale.quantity();
        stockLevels.put(sale.sku(), remaining);
    }

    public void recordSaleSynchronized(SaleRequest sale) {
        synchronized (lock) {
            int remaining = stockLevels.getOrDefault(sale.sku(), 0) - sale.quantity();
            stockLevels.put(sale.sku(), remaining);
        }
    }

    public int remainingUnsafe(String sku) {
        return stockLevels.getOrDefault(sku, 0);
    }

    public int remaining(String sku) {
        synchronized (lock) {
            return stockLevels.getOrDefault(sku, 0);
        }
    }

    public Map<String, Integer> snapshot() {
        synchronized (lock) {
            return Map.copyOf(stockLevels);
        }
    }
}
