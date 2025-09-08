package com.teamdroid.nikit.shared.audit;

import com.teamdroid.nikit.entity.Audit;

import java.time.Instant;

public class AuditFactory {
    public static Audit create(String userId) {
        return new Audit(userId, null, Instant.now(), null);
    }

    public static Audit update(Audit audit, String userId) {
        audit.setUpdatedBy(userId);
        audit.setUpdatedAt(Instant.now());
        return audit;
    }
}
