/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch . All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.service.db.repository;

import org.impensa.service.db.entity.Org;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *
 * @author manosahu
 */
public interface OrgRepository extends GraphRepository<Org>{

    Org findByOrgId(String orgId);

    Iterable<Org> findByOrgNameLike(String orgName);
}
