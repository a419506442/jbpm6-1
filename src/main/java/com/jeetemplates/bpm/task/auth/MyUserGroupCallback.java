/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jeetemplates.bpm.task.auth;

import java.util.Collections;
import java.util.List;
import javax.enterprise.inject.Alternative;
import org.kie.internal.task.api.UserGroupCallback;

/**
 * @author paoesco
 */
@Alternative
public class MyUserGroupCallback implements UserGroupCallback {

    @Override
    public boolean existsGroup(String groupId) {
        return true;
    }

    @Override
    public boolean existsUser(String userId) {
        return true;
    }

    @Override
    public List<String> getGroupsForUser(String userId, List<String> groupIds, List<String> allExistingGroupIds) {
        return Collections.EMPTY_LIST;
    }

}
