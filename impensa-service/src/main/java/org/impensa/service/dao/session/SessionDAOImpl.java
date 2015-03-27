/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.impensa.service.dao.session;

import java.util.HashSet;
import java.util.Set;
import org.common.bean.BeanConverter;
import org.commons.collections.CollectionUtil;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.string.StringUtil;
import org.impensa.service.db.entity.SessionEntity;
import org.impensa.service.db.repository.SessionRepository;
import org.impensa.service.exception.BeanConversionErrorCode;
import org.impensa.service.exception.ImpensaException;
import org.impensa.service.exception.ValidationErrorCode;
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
    public SessionDMO findByUserId(String userId) throws ImpensaException {
        if (StringUtil.isNullOrEmpty(userId)) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL_OR_EMPTY).set("userId", "null or empty");
        }
        SessionEntity sessionEntity = this.getSessionRepository().findByUserId(userId);
        return this.convertFrom(sessionEntity);
    }

    @Override
    public SessionEntity convertTo(SessionDMO sessionDMO) throws ImpensaException {
        if(sessionDMO == null){
            return null;
        }
        SessionEntity sessionEntity;
        try {
            sessionEntity = BeanConverter.toMappingBean(sessionDMO, SessionEntity.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.TO_MAPPING_BEAN)
                    .set("sessionDMO", sessionDMO);
        }
        return sessionEntity;
    }

    @Override
    public SessionDMO convertFrom(SessionEntity sessionEntity) throws ImpensaException {
        if(sessionEntity == null){
            return null;
        }
        SessionDMO sessionDMO;
        try {
            sessionDMO = BeanConverter.fromMappingBean(sessionEntity, SessionDMO.class);
        } catch (Exception ex) {
            throw ImpensaException.wrap(ex)
                    .setErrorCode(BeanConversionErrorCode.TO_MAPPING_BEAN)
                    .set("sessionEntity", sessionEntity);
        }

        return sessionDMO;
    }

    @Override
    public Set<SessionDMO> convertFrom(Set<SessionEntity> sessions) throws ImpensaException {
        if(CollectionUtil.isNullOrEmpty(sessions)){
            return null;
        }
        Set<SessionDMO> allSessions = new HashSet<SessionDMO>();
        if (sessions != null) {
            for (SessionEntity sessionEntity : sessions) {
                allSessions.add(this.convertFrom(sessionEntity));
            }
        }
        return allSessions;
    }

    @Override
    public SessionDMO persistSession(SessionDMO sessionDMO) throws ImpensaException {
        if (sessionDMO == null) {
            throw new ImpensaException(ValidationErrorCode.VALUE_NULL).set("sessionDMO", "null");
        }
        SessionEntity sessionEntity = this.convertTo(sessionDMO);
        this.getSessionRepository().save(sessionEntity);
        return sessionDMO;

    }

}
