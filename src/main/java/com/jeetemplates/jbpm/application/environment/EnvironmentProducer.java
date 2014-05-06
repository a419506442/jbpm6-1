/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jeetemplates.jbpm.application.environment;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.jbpm.kie.services.api.IdentityProvider;
import org.jbpm.runtime.manager.impl.cdi.InjectableRegisterableItemsFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.cdi.qualifier.PerProcessInstance;
import org.kie.internal.runtime.manager.cdi.qualifier.PerRequest;
import org.kie.internal.runtime.manager.cdi.qualifier.Singleton;
import org.kie.internal.task.api.UserGroupCallback;

/**
 * Produces all the environment for jBPM.
 *
 * @author paoesco
 */
public class EnvironmentProducer {

    // Use PersistenceUnit in a container environment
    @PersistenceUnit(unitName = "org.jbpm.persistence.jpa")
    @Produces
    private EntityManagerFactory entityManagerFactory;

    @Inject
    private UserGroupCallback userGroupCallback;

    @Inject
    private BeanManager beanManager;

    /**
     * Produces an entity manager from the entity manager favtory.
     *
     * @param entityManagerFactory
     * @return
     */
    @Produces
    @RequestScoped
    public EntityManager produceEntityManager(EntityManagerFactory entityManagerFactory) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em;
    }

    /**
     * Closes entity manager.
     *
     * @param em
     */
    public void close(@Disposes EntityManager em) {
        em.close();
    }

    /**
     * Produces the runtime manager. The production of the runtime manager has
     * to be called once in the entire application for each strategy. However,
     * jBPM will complain that a new runtime manager with same strategy already
     * exists.
     *
     * <pre>
     * Singleton strategy - instructs RuntimeManager to maintain single instance of RuntimeEngine (and in turn single instance of KieSession and TaskService). Access to the RuntimeEngine is synchronized and by that thread safe although it comes with a performance penalty due to synchronization. This strategy is similar to what was available by default in jBPM version 5.x and it's considered easiest strategy and recommended to start with.
     * </pre>
     * <pre>
     * Per request strategy - instructs RuntimeManager to provide new instance of RuntimeEngine for every request. As request RuntimeManager will consider one or more invocations within single transaction. It must return same instance of RuntimeEngine within single transaction to ensure correctness of state as otherwise operation done in one call would not be visible in the other. This is sort of "stateless" strategy that provides only request scope state and once request is completed RuntimeEngine will be permanently destroyed - KieSession information will be removed from the database in case persistence was used.
     * </pre>
     * <pre>
     * Per process instance strategy - instructs RuntimeManager to maintain a strict relationship between KieSession and ProcessInstance. That means that KieSession will be available as long as the ProcessInstance that it belongs to is active. This strategy provides the most flexible approach to use advanced capabilities of the engine like rule evaluation in isolation (for given process instance only), maximum performance and reduction of potential bottlenecks intriduced by synchronization; and at the same time reduces number of KieSessions to the actual number of process instances rather than number of requests (in contrast to per request strategy).
     * </pre>
     *
     * @param entityManagerFactory
     * @return
     */
    @Produces
    @Singleton
    @PerRequest
    @PerProcessInstance
    public RuntimeEnvironment produceRuntimeEnvironment(EntityManagerFactory entityManagerFactory) {
        return RuntimeEnvironmentBuilder.Factory.get()
                .newDefaultBuilder()
                .entityManagerFactory(entityManagerFactory)
                .userGroupCallback(userGroupCallback)
                .registerableItemsFactory(InjectableRegisterableItemsFactory.getFactory(beanManager, null))
                .addAsset(ResourceFactory.newClassPathResource("processes/simpleProcess.bpmn"), ResourceType.BPMN2)
                .get();

    }
}
