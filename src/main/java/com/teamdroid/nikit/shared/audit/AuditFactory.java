package com.teamdroid.nikit.shared.audit;

import com.teamdroid.nikit.entity.Audit;

import java.time.Instant;

public class AuditFactory {
    public static Audit create(String userId) {
        Instant now = Instant.now();
        return new Audit(userId, userId, now, now);
    }
}
