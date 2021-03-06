package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.SubjectDao;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.SubjectService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * The type Subject service.
 * Class implements base service and subject service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class SubjectServiceImpl implements SubjectService {

    /**
     * The constant instance.
     */
    public static SubjectServiceImpl instance;
    private static Logger logger = LogManager.getLogger();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SubjectServiceImpl getInstance() {
        if (instance == null) {
            instance = new SubjectServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        throw new UnsupportedOperationException("Impossible create subject");
    }

    @Override
    public Map<String, String> checkParameters(Map<String, String> parameters) throws ServiceException {
        throw new UnsupportedOperationException("Subject parameters is always true");
    }

    @Override
    public List<Subject> findAllSubjects() throws ServiceException {
        SubjectDao dao = SubjectDaoImpl.getInstance();
        try {
            List<Subject> subjects = dao.findAll();
            return subjects;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding all subjects", e);
            throw new ServiceException(e);
        }
    }
}