/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.db;

import org.neo4j.graphdb.GraphDatabaseService;
import static org.impensa.db.GraphDatabaseConstant.*;
/**
 * So each tenant would have its own database.
 * The idea is each tenant would be isolated from each other.
 * Let them worry about their own data.
 * The other strategy would have been as follows.
 * Each db entity needs to be aware of tenantId. this would have been tedious
 * Hence fresh db per each tenant is more viable.
 * 
 * @author msahu98
 */
public class TenantGraphDatabseService {

    private String tenantId;

    private GraphDatabaseService graphDatabaseService;
    
    public String getTenantGraphDataServiceBeanName(){
        StringBuilder sb = new StringBuilder();
        sb.append(TENANT_GRAPH_DATABASE_SERVICE_BEAN_NAME);
        sb.append(this.getTenantId());
        return sb.toString();
    }
    
   public String getTenantGraphDatabasePath(){
       StringBuilder sb = new StringBuilder();
       sb.append(TENANT_DATABASE_PATH);
       sb.append(this.getTenantId());
       return sb.toString();
   }

    public GraphDatabaseService getGraphDatabaseService() {
        return graphDatabaseService;
    }

    public void setGraphDatabaseService(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.tenantId != null ? this.tenantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TenantGraphDatabseService other = (TenantGraphDatabseService) obj;
        if ((this.tenantId == null) ? (other.tenantId != null) : !this.tenantId.equals(other.tenantId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TenantGraphDatabseService{" + "tenantId=" + tenantId + ", graphDatabaseService=" + graphDatabaseService + '}';
    }
    
    
}
