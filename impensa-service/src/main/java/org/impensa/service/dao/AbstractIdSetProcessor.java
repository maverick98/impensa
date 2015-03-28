/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.dao;

import java.util.HashSet;
import java.util.Set;
import org.commons.collections.CollectionUtil;
import org.impensa.service.exception.ImpensaException;
import org.impensa.service.exception.ValidationErrorCode;

/**
 *
 * @author manosahu
 */
public abstract class AbstractIdSetProcessor {

    private Set<String> ids = new HashSet<String>();

    public AbstractIdSetProcessor(Set<String> ids) {
        this.ids = ids;
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }

    public void process() throws ImpensaException {
        if (CollectionUtil.isNullOrEmpty(this.getIds())) {
            return;
        }

        for (String id : this.getIds()) {
            this.onIdVisit(id);
        }

    }

    public abstract void onIdVisit(String id) throws ImpensaException;

}
