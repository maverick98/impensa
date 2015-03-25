/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.impensa.service.dao;

import org.impensa.service.dao.exception.DAOException;

/**
 *
 * @author manosahu
 */
public interface ITxnExecutor {

    public  void execute() throws DAOException;

    public void createTxn() throws DAOException;
}
