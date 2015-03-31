/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.db.repository;

import org.impensa.db.entity.TxnDataEntity;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *@TODO this is boring. Can we generate these repository code...
 * irritating to create these classes myself.
 * @author manosahu
 */
public interface AttributeRepository extends GraphRepository<TxnDataEntity> {

    TxnDataEntity findByName(String name);

    Iterable<TxnDataEntity> findByNameLike(String name);
}
