package com.jeetemplates.jbpm.application.environment;

import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.jbpm.kie.services.api.IdentityProvider;

/**
 * Implementation of IdentityProvider used by CDI.
 *
 * @author paoesco
 */
@ApplicationScoped
public class MyIdentityProvider implements IdentityProvider {

    @Override
    public String getName() {
        return "";
    }

    @Override
    public List<String> getRoles() {
        return Collections.EMPTY_LIST;
    }

}
