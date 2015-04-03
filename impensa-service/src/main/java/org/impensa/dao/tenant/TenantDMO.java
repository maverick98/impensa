/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.dao.tenant;

import javax.xml.bind.annotation.XmlRootElement;
import org.common.bean.MappingBean;
import org.impensa.db.entity.TenantEntity;

/**
 * This will be accessible across the wire. So let's be careful with the naming
 * of variables
 *
 * @author manosahu
 */
@XmlRootElement
@MappingBean(name = TenantEntity.class)
public class TenantDMO implements Comparable<TenantDMO> {

    private String tenantId;

    private String password;
    
    private String confirmPassword;
    
    private String encryptedPassword;

    private String name;

    private String email;

    private String phone;

    private String address;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

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

    @Override
    public int compareTo(TenantDMO o) {
        return this.getTenantId().compareTo(o.getTenantId());
    }

}
