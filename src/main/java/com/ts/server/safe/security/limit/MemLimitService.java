package com.ts.server.safe.security.limit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过内存实现{@link LoginLimitService}
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class MemLimitService implements LoginLimitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemLimitService.class);

    private final ConcurrentMap<String, ConcurrentMap<String, AtomicInteger>> dateCounts = new ConcurrentHashMap<>(5);

    @Override
    public int getFail(String username) {
        String day = getDay();

        ConcurrentMap<String, AtomicInteger> dateCount = dateCounts.get(day);
        if(dateCount == null){
            return 0;
        }

        AtomicInteger count = dateCount.get(username);
        return count == null? 0: count.get();
    }

    @Override
    public int incFail(String username) {
        String day = getDay();

        ConcurrentMap<String, AtomicInteger> dateCount = dateCounts.get(day);
        if(dateCount == null){
            ConcurrentMap<String, AtomicInteger> map = new ConcurrentHashMap<>(50);
            dateCount = dateCounts.putIfAbsent(day, map);
            dateCount = dateCount != null? dateCount: map;
        }

        AtomicInteger count = dateCount.get(username);
        if(count == null){
            AtomicInteger tmp = new AtomicInteger(0);
            count = dateCount.putIfAbsent(username, tmp);
            count = count != null? count: tmp;
        }

        int c =  count.incrementAndGet();

        LOGGER.debug("Mem limit inc login fail count username={}, count={}", username, c);

        return c;
    }

    @Override
    public void resetFail(String username) {
        String day = getDay();

        ConcurrentMap<String, AtomicInteger> dateCount = dateCounts.get(day);
        if(dateCount == null){
            return;
        }

        AtomicInteger count = dateCount.get(username);
        if(count == null){
            return;
        }

        count.set(0);
    }

    @Override
    public void clearExpired() {
        for(int i = 0; i < 3; i++){
            String day = formatDay(LocalDate.now().plusDays((i + 1) * -1));
            boolean has =dateCounts.remove(day) != null;
            LOGGER.debug("Mem limit delete day={},has={}", day, has);
        }
    }
}
