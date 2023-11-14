package com.healontrip.scheduler;

import com.healontrip.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenPurgeTask {

    @Autowired
    private TokenRepository tokenRepository;

    //@Scheduled(cron = "0 32 20 * * *") // 20:32 everyday
    @Scheduled(initialDelay = 1000 * 60 * 10, fixedRate = 1000 * 60 * 10)
    public void purgeExpired() {
        // delete expired reset tokens
        tokenRepository.deleteAllById(tokenRepository.findAllExpiredTokens());
    }
}
