package com.jeetemplates.jbpm.application.environment;

import java.util.HashMap;
import java.util.Map;
import org.jbpm.bpmn2.handler.ServiceTaskHandler;
import org.jbpm.runtime.manager.api.WorkItemHandlerProducer;
import org.kie.api.runtime.process.WorkItemHandler;

/**
 * This implementation is used by CDI to register some handlers. Handlers will
 * be used by KieSession.
 *
 * @author paoesco
 */
public class DefaultWorkItemHandlerProducer implements WorkItemHandlerProducer {

    @Override
    public Map<String, WorkItemHandler> getWorkItemHandlers(String identifier, Map<String, Object> params) {
        Map<String, WorkItemHandler> handlers = new HashMap<>();
        handlers.put("Service Task", new ServiceTaskHandler());
        // Add more handlers if needed
        return handlers;
    }

}
