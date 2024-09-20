package com.drainshawty.lab1;

import org.camunda.bpm.engine.IdentityService;

public class Utils {
    public static String getUserEmail(IdentityService identityService, String userId) {
        return identityService.createUserQuery().userId(userId).singleResult().getEmail();
    }
}
