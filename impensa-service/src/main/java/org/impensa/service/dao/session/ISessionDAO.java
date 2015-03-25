/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.dao.session;

import java.util.Set;
import org.impensa.service.db.entity.SessionEntity;

/**
 *
 * @author manosahu
 */
public interface ISessionDAO {
    
    public SessionDMO persistSession(SessionDMO sesionDMO) throws SessionDAOException;
    

    public SessionDMO findByUserId(String userId) throws SessionDAOException;
    
    /**
     * So client code make calls to this API for conversion.
     * Let's us stop her directly invoking DomainEntityConverter. That being static
     * , we would end up having lesser control.
     * @param sessionDMO
     * @return
     * @throws SessionDAOException 
     */
    public SessionEntity convertTo(final SessionDMO sessionDMO) throws SessionDAOException ;
    
    /**
     * reciprocal of convertTo method.
     * @param sessionEntity
     * @return 
     * @throws org.impensa.service.dao.session.SessionDAOException 
     */
    public SessionDMO convertFrom(final SessionEntity sessionEntity) throws SessionDAOException ;
    
    /**
     * 
     * @param sessions
     * @return 
     * @throws org.impensa.service.dao.session.SessionDAOException 
     */
    public Set<SessionDMO> convertFrom(Set<SessionEntity> sessions) throws SessionDAOException;

}
