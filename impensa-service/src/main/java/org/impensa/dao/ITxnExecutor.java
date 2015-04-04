/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.impensa.dao;

import org.impensa.exception.ImpensaException;
import org.neo4j.graphdb.GraphDatabaseService;

/**
 *
 * @author manosahu
 */
public interface ITxnExecutor {

    public  void execute() throws ImpensaException;

    public void createTxn(GraphDatabaseService databaseService) throws ImpensaException;
}
