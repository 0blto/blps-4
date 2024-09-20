package com.drainshawty.lab1.config;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaConfig {

    @Bean
    public CommandLineRunner createUsersAndGroups(IdentityService identityService, AuthorizationService authorizationService) {
        return args -> {
            if (identityService.createGroupQuery().groupId("user").singleResult() == null) {
                Group group = identityService.newGroup("user");
                group.setName("Users Group");
                group.setType("WORKFLOW");
                identityService.saveGroup(group);
            }

            if (identityService.createUserQuery().userId("yarik").singleResult() == null) {
                User user = identityService.newUser("yarik");
                user.setFirstName("yarik");
                user.setLastName("yarik");
                user.setPassword("yarik");
                user.setEmail("hopegasgas@gmail.com");
                identityService.saveUser(user);

                identityService.createMembership("yarik", "user");
            }

            if (authorizationService.createAuthorizationQuery().groupIdIn("user")
                    .resourceType(Resources.PROCESS_DEFINITION.resourceType())
                    .resourceId("*").count() == 0) {

                Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
                auth.setGroupId("user");
                auth.addPermission(Permissions.CREATE_INSTANCE);
                auth.addPermission(Permissions.READ);
                auth.setResource(Resources.PROCESS_DEFINITION);
                auth.setResourceId("*");
                authorizationService.saveAuthorization(auth);
            }

            if (authorizationService.createAuthorizationQuery().groupIdIn("user")
                    .resourceType(Resources.PROCESS_INSTANCE.resourceType())
                    .resourceId("*").count() == 0) {

                Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
                auth.setGroupId("user");
                auth.addPermission(Permissions.READ);
                auth.addPermission(Permissions.CREATE);
                auth.setResource(Resources.PROCESS_INSTANCE);
                auth.setResourceId("*");
                authorizationService.saveAuthorization(auth);
            }

            if (authorizationService.createAuthorizationQuery().groupIdIn("user")
                    .resourceType(Resources.TASK.resourceType())
                    .resourceId("*").count() == 0) {

                Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
                auth.setGroupId("user");
                auth.addPermission(Permissions.READ);
                auth.setResource(Resources.TASK);
                auth.setResourceId("*");
                authorizationService.saveAuthorization(auth);
            }

            if (authorizationService.createAuthorizationQuery().groupIdIn("user")
                    .resourceType(Resources.APPLICATION.resourceType())
                    .resourceId("tasklist").count() == 0) {

                Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
                auth.setGroupId("user");
                auth.addPermission(Permissions.ACCESS);
                auth.setResource(Resources.APPLICATION);
                auth.setResourceId("tasklist");
                authorizationService.saveAuthorization(auth);
            }

            if (authorizationService.createAuthorizationQuery().groupIdIn("user")
                    .resourceType(Resources.FILTER.resourceType())
                    .resourceId("*").count() == 0) {

                Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
                auth.setGroupId("user");
                auth.addPermission(Permissions.CREATE);
                auth.addPermission(Permissions.READ);
                auth.setResource(Resources.FILTER);
                auth.setResourceId("*");
                authorizationService.saveAuthorization(auth);
            }
        };
    }
}
