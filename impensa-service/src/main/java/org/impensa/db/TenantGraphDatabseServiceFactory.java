/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.db;

import org.commons.string.StringUtil;
import org.impensa.AppContainer;
import org.impensa.exception.ImpensaException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 *
 * @author msahu98
 */
public class TenantGraphDatabseServiceFactory {

    /**
     * This just creates TenantGraphDatabaseService object
     * It invokes GraphDatabaseUtil.createGraphDatabaseService to create the
     * actual GraphDatabaseService object
     * @param tenantId
     * @return 
     */
    public static TenantGraphDatabseService createTenantGraphDatabaseService(String tenantId) {
        if (StringUtil.isNullOrEmpty(tenantId)) {
            //TODO pass proper errorcode here
            throw new ImpensaException("TenantId can NOT be null or empty", null);
        }
        TenantGraphDatabseService tenantGraphDatabseService = new TenantGraphDatabseService();
        tenantGraphDatabseService.setTenantId(tenantId);
        GraphDatabaseService graphDatabaseService = GraphDatabaseUtil.createGraphDatabaseService(tenantGraphDatabseService.getTenantGraphDatabasePath());
        tenantGraphDatabseService.setGraphDatabaseService(graphDatabaseService);
        return tenantGraphDatabseService;
    }

    /**
     * This creates the required BeanDefinition for spring
     * The very reason for the existence of this method is that
     * we want to delegate the lifecycle mgmt of this TenantGraphDatabaseService
     * to Spring. Otherwise we would have to maintain some kind of cache for all tenant's db service
     * which in turn would have been cumbersome and bug prone.
     * @param tenantGraphDatabseService
     * @return 
     */
    public static BeanDefinition createTenantGraphDatabaseServiceBeanDefinition(TenantGraphDatabseService tenantGraphDatabseService) {
        BeanDefinition definition = new RootBeanDefinition(TenantGraphDatabseService.class);
        definition.setAttribute("graphDatabaseService", tenantGraphDatabseService.getGraphDatabaseService());
        definition.setAttribute("tenantId", tenantGraphDatabseService.getTenantId());
        return definition;

    }

    /**
     * This imply registers the tenantGraphDatabaseSerivce with the Spring.
     * So the sequence of API invocation would as follows
     * createTenantGraphDatabaseService->registerTenantGraphDatabaseService->createTenantGraphDatabaseServiceBeanDefinition
     * @param tenantGraphDatabseService 
     */
    public static void registerTenantGraphDatabaseService(TenantGraphDatabseService tenantGraphDatabseService) {
        BeanDefinition definition = TenantGraphDatabseServiceFactory.createTenantGraphDatabaseServiceBeanDefinition(tenantGraphDatabseService);
        AppContainer.getInstance().getAppContext().registerBeanDefinition(tenantGraphDatabseService.getTenantGraphDataServiceBeanName(), definition);

    }

}
