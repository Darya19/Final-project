package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SubjectServiceImpl implements BaseService<Subject> {

    public static EnrolleeMarkRegisterServiceImpl instance;
    private static Logger logger = LogManager.getLogger();

    public static EnrolleeMarkRegisterServiceImpl getInstance() {
        if (instance == null) {
            instance = new EnrolleeMarkRegisterServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        return false;
    }

    @Override
    public int remove(Subject user) throws ServiceException {
        return 0;
    }

    @Override
    public Optional<Subject> find(String value) throws ServiceException {
        return Optional.empty();
    }

    public List<Subject> findAllSubjects() throws ServiceException {
        SubjectDaoImpl dao = SubjectDaoImpl.getInstance();
        try {
            List<Subject> subjects = dao.findAll();
                return subjects;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(int value, Map<String, String> parameters) throws ServiceException {
        return false;
    }
}
