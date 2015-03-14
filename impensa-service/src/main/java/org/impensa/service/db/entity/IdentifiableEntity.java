/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu. All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.db.entity;

import org.springframework.data.neo4j.annotation.GraphId;

/**
 * @author: manosahu
 */
public abstract class IdentifiableEntity {
    @GraphId
    private Long graphId;

    public Long getGraphId() {
        return graphId;
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof IdentifiableEntity && graphId.equals(((IdentifiableEntity) obj).getGraphId() );
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
