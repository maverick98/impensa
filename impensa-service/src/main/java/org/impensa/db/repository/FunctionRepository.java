/*
 * This file is part of impensa.
 * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
 *
 * 1) Modify it if you can understand.
 * 2) If you distribute a modified version, you must do it at your own risk.
 *
 */
package org.impensa.db.repository;

import org.impensa.db.entity.FunctionEntity;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 *
 * @author manosahu
 */
@EnableNeo4jRepositories
public interface FunctionRepository extends GraphRepository<FunctionEntity>{

    FunctionEntity findByFunctionName(String functionName);

    Iterable<FunctionEntity> findByFunctionNameLike(String functionName);
}
