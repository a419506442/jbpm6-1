/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jeetemplates.jbpm.application;

import java.util.Map;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.runtime.manager.context.EmptyContext;

/**
 * Use ProcessManagerBean (custom singleton EJB) and only this class to manage
 * processes. Implement all calls to jBPM API here (for transactions).
 *
 * @author paoesco
 */
@Singleton
public class ProcessManagerBean {

    @Inject
    @org.kie.internal.runtime.manager.cdi.qualifier.Singleton
    private RuntimeManager singletonRuntimeManager;

    /**
     * Starts a process.
     *
     * @param processId
     */
    public void startProcess(final String processId) {
        startProcess(processId, null);
    }

    /**
     * Starts a process with parameters.
     *
     * @param processId
     * @param parameters
     */
    public void startProcess(final String processId, final Map<String, Object> parameters) {
        RuntimeEngine runtimeEngine = singletonRuntimeManager.getRuntimeEngine(EmptyContext.get());
        KieSession kieSession = runtimeEngine.getKieSession();
        kieSession.startProcess(processId, parameters);
    }

}
