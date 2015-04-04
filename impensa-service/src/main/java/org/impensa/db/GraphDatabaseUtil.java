/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.db;

import java.io.File;
import org.commons.string.StringUtil;
import org.impensa.AppContainer;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import static org.impensa.db.GraphDatabaseConstant.*;
import org.impensa.exception.ImpensaException;

/**
 *
 * @author msahu98
 */
public class GraphDatabaseUtil {



    public static GraphDatabaseService getMainGraphDatabseService() {
        GraphDatabaseService graphDatabaseService;
        graphDatabaseService = AppContainer.getInstance().getBean(MAIN_GRAPH_DATABASE_SERVICE_BEAN_NAME, GraphDatabaseService.class);

        return graphDatabaseService;
    }

    public static boolean shutdown(final GraphDatabaseService graphDatabaseService) {
        graphDatabaseService.shutdown();
        return true;
    }

   

    public static GraphDatabaseService startGraphDatabaseService(final String dbPath) {
        if (StringUtil.isNullOrEmpty(dbPath)) {
            //TODO pass proper errorcode here
            throw new ImpensaException("dbPath can NOT be null or empty", null);
        }
        GraphDatabaseService service;
        File dbFilePath = new File(dbPath);
        if (dbFilePath.exists()) {
            service = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        } else {
            service = null;
        }

        return service;
    }

    public static GraphDatabaseService createGraphDatabaseService(final String dbPath) {
        if (StringUtil.isNullOrEmpty(dbPath)) {
            //TODO pass proper errorcode here
            throw new ImpensaException("dbPath can NOT be null or empty", null);
        }

        System.out.println("Creating/Starting database service");
        GraphDatabaseService service = startGraphDatabaseService(dbPath);
        if (service != null) {
            System.out.println("The db path {" + dbPath + "} exists... It will be wiped off completely....");
            File dbFilePath = new File(dbPath);
            dbFilePath.delete();
        }
        service = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        if (service != null) {
            System.out.println("Database created/started at  {" + dbPath + "} successfully!!!");
        } else {
            System.out.println("Database could NOT be created/started at  {" + dbPath + "}");
        }
        return service;

    }
}
