/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.dao.session;

import java.util.HashSet;
import java.util.Set;
import org.common.bean.BeanConverter;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.impensa.service.db.entity.SessionEntity;
import org.impensa.service.db.repository.SessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author manosahu
 */
@Component
public class SessionDAOImpl implements ISessionDAO {

    private static final ILogger logger = LoggerFactory.getLogger(SessionDAOImpl.class.getName());
    
    
    @Autowired
    private SessionRepository sessionRepository;

    public SessionRepository getSessionRepository() {
        return sessionRepository;
    }

    public void setSessionRepository(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public SessionDMO findByUserId(String userId) throws SessionDAOException {
        SessionEntity sessionEntity = this.getSessionRepository().findByUserId(userId);
        return this.convertFrom(sessionEntity);
    }

    @Override
    public SessionEntity convertTo(SessionDMO sessionDMO) throws SessionDAOException {
        SessionEntity sessionEntity;
        try {
            sessionEntity = BeanConverter.toMappingBean(sessionDMO, SessionEntity.class);
        } catch (Exception ex) {
            logger.error("error while converting to entity object " + sessionDMO, ex);
            throw new SessionDAOException("error while converting to entity object " + sessionDMO, ex);
        }
        return sessionEntity;
    }

    @Override
    public SessionDMO convertFrom(SessionEntity sessionEntity) throws SessionDAOException {
        SessionDMO sessionDMO;
        try {
            sessionDMO = BeanConverter.fromMappingBean(sessionEntity, SessionDMO.class);
        } catch (Exception ex) {
            logger.error("error while converting from entity object " + sessionEntity, ex);
            throw new SessionDAOException("error while converting from entity object " + sessionEntity, ex);
        }

        return sessionDMO;
    }

    @Override
    public Set<SessionDMO> convertFrom(Set<SessionEntity> sessions) throws SessionDAOException {
        Set<SessionDMO> allSessions = new HashSet<SessionDMO>();
        if (sessions != null) {
            for (SessionEntity sessionEntity : sessions) {
                allSessions.add(this.convertFrom(sessionEntity));
            }
        }
        return allSessions;
    }

    @Override
    public SessionDMO persistSession(SessionDMO sessionDMO) throws SessionDAOException {
        SessionEntity sessionEntity = this.convertTo(sessionDMO);
        this.getSessionRepository().save(sessionEntity);
        return sessionDMO;
        
    }

}
