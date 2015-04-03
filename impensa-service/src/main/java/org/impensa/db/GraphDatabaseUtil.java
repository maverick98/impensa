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
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import static org.impensa.db.GraphDatabaseConstant.*;
import org.impensa.exception.ImpensaException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 *
 * @author msahu98
 */
public class GraphDatabaseUtil {

    public static GraphDatabaseService createMainGraphDatabaseService() {
        return createGraphDatabaseService(MAIN_DATABASE_PATH);
    }

    public static GraphDatabaseService getMainGraphDatabseService() {
        GraphDatabaseService graphDatabaseService;
        graphDatabaseService = AppContainer.getInstance().getBean(MAIN_GRAPH_DATABASE_SERVICE_BEAN_NAME, GraphDatabaseService.class);

        return graphDatabaseService;
    }



    public static boolean shutdown(final GraphDatabaseService graphDatabaseService) {
        graphDatabaseService.shutdown();
        return true;
    }

    public static boolean shutdown(final String tenantId) {
        if (StringUtil.isNullOrEmpty(tenantId)) {
            //TODO pass proper errorcode here
            throw new ImpensaException("TenantId can NOT be null or empty", null);
        }
        return false;
    }

    public static GraphDatabaseService createTenantGraphDatabaseService(final String tenantId) {
        if (StringUtil.isNullOrEmpty(tenantId)) {
            //TODO pass proper errorcode here
            throw new ImpensaException("TenantId can NOT be null or empty", null);
        }
        TenantGraphDatabseService tenantGraphDatabseService = TenantGraphDatabseServiceFactory.createTenantGraphDatabaseService(tenantId);

        BeanDefinition definition = new RootBeanDefinition(TenantGraphDatabseService.class);
        definition.setAttribute("graphDatabaseService", tenantGraphDatabseService.getGraphDatabaseService());
        definition.setAttribute("tenantId", tenantGraphDatabseService.getTenantId());

        AppContainer.getInstance().getAppContext().registerBeanDefinition(tenantGraphDatabseService.getTenantGraphDataServiceBeanName(), definition);
        TenantGraphDatabseService gds1 = (TenantGraphDatabseService) AppContainer.getInstance().getBean(tenantGraphDatabseService.getTenantGraphDataServiceBeanName());
        System.out.println(gds1);
        return gds1.getGraphDatabaseService();
    }

    public static void registerTenantGraphDatabaseService(TenantGraphDatabseService tenantGraphDatabseService) {
        BeanDefinition definition = TenantGraphDatabseServiceFactory.createTenantGraphDatabaseServiceBeanDefinition(tenantGraphDatabseService);
        AppContainer.getInstance().getAppContext().registerBeanDefinition(tenantGraphDatabseService.getTenantGraphDataServiceBeanName(), definition);

    }

    public static GraphDatabaseService createGraphDatabaseService(final String dbPath) {
        if (StringUtil.isNullOrEmpty(dbPath)) {
            //TODO pass proper errorcode here
            throw new ImpensaException("dbPath can NOT be null or empty", null);
        }
        System.out.println("Creating/Starting database service");
        GraphDatabaseService service = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        if (service != null) {
            System.out.println("Database created/started at  {" + dbPath + "} successfully!!!");
        } else {
            System.out.println("Database could NOT be created/started at  {" + dbPath + "}");
        }
        return service;

    }
}
