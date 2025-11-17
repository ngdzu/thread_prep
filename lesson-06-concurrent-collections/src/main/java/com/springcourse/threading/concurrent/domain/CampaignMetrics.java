package com.springcourse.threading.concurrent.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public final class CampaignMetrics {

    private final ConcurrentHashMap<String, LongAdder> clicksByCountry = new ConcurrentHashMap<>();
    private final LongAdder totalClicks = new LongAdder();
    private final AtomicLong totalDwellMillis = new AtomicLong();

    public void recordClick(String country, long dwellMillis) {
        clicksByCountry.computeIfAbsent(country, key -> new LongAdder()).increment();
        totalClicks.increment();
        totalDwellMillis.addAndGet(dwellMillis);
    }

    public long totalClicks() {
        return totalClicks.sum();
    }

    public double averageDwell() {
        long clicks = totalClicks.sum();
        return clicks == 0 ? 0 : (double) totalDwellMillis.get() / clicks;
    }

    public Map<String, Long> clicksPerCountry() {
        return clicksByCountry.entrySet().stream()
                .collect(ConcurrentHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue().sum()),
                        ConcurrentHashMap::putAll);
    }
}
