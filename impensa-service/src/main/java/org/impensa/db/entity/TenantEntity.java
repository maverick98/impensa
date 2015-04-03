/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.db.entity;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

/**
 *
 * @author msahu98
 */
@NodeEntity
public class TenantEntity extends IdentifiableEntity implements Comparable<TenantEntity> {

    @Indexed(unique = true)
    private String tenantId;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "encryptedPassword")
    private String encryptedPassword;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "name")
    private String name;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "email")
    private String email;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "phone")
    private String phone;
    @Indexed(indexType = IndexType.FULLTEXT, indexName = "address")
    private String address;

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.tenantId != null ? this.tenantId.hashCode() : 0);
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
        final TenantEntity other = (TenantEntity) obj;
        if ((this.tenantId == null) ? (other.tenantId != null) : !this.tenantId.equals(other.tenantId)) {
            return false;
        }
        return true;
    }
    

    @Override
    public int compareTo(TenantEntity o) {
        return this.getTenantId().compareTo(o.getTenantId());
    }

}
