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

    public static BeanDefinition createTenantGraphDatabaseServiceBeanDefinition(TenantGraphDatabseService tenantGraphDatabseService) {
        BeanDefinition definition = new RootBeanDefinition(TenantGraphDatabseService.class);
        definition.setAttribute("graphDatabaseService", tenantGraphDatabseService.getGraphDatabaseService());
        definition.setAttribute("tenantId", tenantGraphDatabseService.getTenantId());
        return definition;

    }

    public static void registerTenantGraphDatabaseService(TenantGraphDatabseService tenantGraphDatabseService) {
        BeanDefinition definition = TenantGraphDatabseServiceFactory.createTenantGraphDatabaseServiceBeanDefinition(tenantGraphDatabseService);
        AppContainer.getInstance().getAppContext().registerBeanDefinition(tenantGraphDatabseService.getTenantGraphDataServiceBeanName(), definition);

    }

}
