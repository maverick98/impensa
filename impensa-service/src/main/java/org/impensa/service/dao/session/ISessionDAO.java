/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.dao.session;

import java.util.Set;
import org.impensa.service.db.entity.SessionEntity;
import org.impensa.service.exception.ImpensaException;

/**
 *
 * @author manosahu
 */
public interface ISessionDAO {
    
    public SessionDMO persistSession(SessionDMO sesionDMO) throws ImpensaException;
    

    public SessionDMO findByUserId(String userId) throws ImpensaException;
    
    /**
     * So client code make calls to this API for conversion.
     * Let's us stop her directly invoking DomainEntityConverter. That being static
     * , we would end up having lesser control.
     * @param sessionDMO
     * @return
     * @throws ImpensaException 
     */
    public SessionEntity convertTo(final SessionDMO sessionDMO) throws ImpensaException ;
    
    /**
     * reciprocal of convertTo method.
     * @param sessionEntity
     * @return 
     * @throws org.impensa.service.dao.session.ImpensaException 
     */
    public SessionDMO convertFrom(final SessionEntity sessionEntity) throws ImpensaException ;
    
    /**
     * 
     * @param sessions
     * @return 
     * @throws org.impensa.service.dao.session.ImpensaException 
     */
    public Set<SessionDMO> convertFrom(Set<SessionEntity> sessions) throws ImpensaException;

}
