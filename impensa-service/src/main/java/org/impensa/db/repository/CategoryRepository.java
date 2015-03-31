/**
 * This file is part of impensa. CopyLeft (C) BigBang<->BigCrunch.All Rights are
 * left.
 *
 * 1) Modify it if you can understand. 2) If you distribute a modified version,
 * you must do it at your own risk.
 *
 */
package org.impensa.db.repository;

import org.impensa.db.entity.CategoryEntity;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *
 * @author manosahu
 */
public interface CategoryRepository extends GraphRepository<CategoryEntity> {

    CategoryEntity findByName(String name);

    Iterable<CategoryEntity> findByNameLike(String name);
}
