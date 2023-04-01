package com.youtubeclonebynattarat.nattaratprojects.Scheduling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserScheduling {

    // Schedule Note
    // 1 => second
    // 2 => minute
    // 3 => hour
    // 4 => day
    // 5 => month
    // 6 => year
    //@Scheduled(cron = "0 * * * * *")
    public void testEveryMinute(){
        log.info("Test-------------------------");
    }
    /**
     * Everyday at 00:00 (UTC Time)
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void testEveryMidNight() {
        // log.info("Mid-Night");
    }

    /**
     * Everyday at 10:50 AM (Thai Time)
     */
    @Scheduled(cron = "0 50 10 * * *", zone = "Asia/Bangkok")
    public void testEverydayNineAM() {
        // log.info("At Specific Time");
    }


}
