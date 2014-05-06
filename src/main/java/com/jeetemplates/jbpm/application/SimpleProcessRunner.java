/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jeetemplates.jbpm.application;

import javax.inject.Inject;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.runtime.manager.cdi.qualifier.Singleton;
import org.kie.internal.runtime.manager.context.EmptyContext;

/**
 * Runs the simple process.
 * @author paoesco
 */
public class SimpleProcessRunner {
    
    @Inject
    @Singleton
    private RuntimeManager runtimeManager;
    
    /**
     * Method to run the simple process.
     */
    public void run(){
        RuntimeEngine engine = runtimeManager.getRuntimeEngine(EmptyContext.get());
        engine.getKieSession().startProcess("simpleProcess");
    }
    
}
