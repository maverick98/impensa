/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao.org;

import javax.xml.bind.annotation.XmlRootElement;
import org.impensa.service.db.entity.Org;
import org.impensa.service.db.entity.mapping.MappingEntity;

/**
 * This will be accessible across the wire. So let's be careful with the naming
 * of variables
 *
 * @author manosahu
 */
@XmlRootElement
@MappingEntity(name = Org.class)
public class OrgDMO {

    private String orgId;

    private String orgName;

    private String orgDescription;

    private Org parentOrg;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public Org getParentOrg() {
        return parentOrg;
    }

    public void setParentOrg(Org parentOrg) {
        this.parentOrg = parentOrg;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.orgId != null ? this.orgId.hashCode() : 0);
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
        final OrgDMO other = (OrgDMO) obj;
        if ((this.orgId == null) ? (other.orgId != null) : !this.orgId.equals(other.orgId)) {
            return false;
        }
        return true;
    }
    
    
}
