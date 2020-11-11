package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.EnrolleeMarkRegisterDaoImpl;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.BaseService;
import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.validator.ProjectValidator;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnrolleeMarkRegisterServiceImpl implements BaseService<EnrolleeMarkRegister> {

    private static final String EMPTY_VALUE = "";

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        return false;
    }

    @Override
    public int remove(EnrolleeMarkRegister value) throws ServiceException {
        return 0;
    }

    @Override
    public Optional<EnrolleeMarkRegister> find(String value) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public boolean update(int value, Map<String, String> parameters) throws ServiceException {
        return false;
    }

    //login register
    public Optional<EnrolleeMarkRegister> findEnrolleeMarkRegister(int enrolleeId) throws ServiceException {
        EnrolleeMarkRegisterDaoImpl dao = EnrolleeMarkRegisterDaoImpl.getInstance();
        try {
            Optional<EnrolleeMarkRegister> markRegister =
                    dao.findEnrolleeMarkRegisterByEnrolleeId(enrolleeId);
            return markRegister;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    //update mar
    public Optional<EnrolleeMarkRegister> updateEnrolleRegister(int  enrolleeId, Map<String, String> parameters)
            throws ServiceException {
        EnrolleeMarkRegisterDaoImpl registerDao = EnrolleeMarkRegisterDaoImpl.getInstance();
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        EnrolleeMarkRegister register = new EnrolleeMarkRegister();
        NumberParser parser = new NumberParser();
        try {
            for (Map.Entry<String, String> pair : parameters.entrySet()) {
                String key = pair.getKey();
                String value = pair.getValue();
                int subjectId = parser.parseToInt(key);
                int markValue = parser.parseToInt(value);
                Optional<Subject> subject = subjectDao.findById(subjectId);
                if (subject.isPresent()) {
                    Subject foundSubject = subject.get();
                    register.put(foundSubject, markValue);
                }
            }
            return registerDao.update(register, enrolleeId) ? Optional.of(register) : Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    //update mar
    public Map<String, String> checkParameters(Map<String, String> parameters) {
        ProjectValidator validator = new ProjectValidator();
        for (Map.Entry<String, String> pair : parameters.entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();
            if (!validator.isMarkValid(value)) {
                parameters.put(key, EMPTY_VALUE);
            }
        }
        return parameters;
    }

    //login register
    public EnrolleeMarkRegister calculateEnrolleeAverageMark(EnrolleeMarkRegister register) {
        Map<Subject, Integer> testsResults = register.getTestsSubjectsAndMarks();
        int averageMark = 0;
        for (Integer mark : testsResults.values()) {
            averageMark += mark;
        }
        averageMark /= testsResults.size();
        register.setAverageMark(averageMark);
        return register;
    }
}
