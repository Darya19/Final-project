package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.EnrolleeMarkRegisterDaoImpl;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.EnrolleeMarkRegisterService;
import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.validator.ProjectValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class EnrolleeMarkRegisterServiceImpl implements EnrolleeMarkRegisterService {

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
    public Optional<EnrolleeMarkRegister> findEnrolleeMarkRegister(int enrolleeId) throws ServiceException {
        EnrolleeMarkRegisterDaoImpl dao = EnrolleeMarkRegisterDaoImpl.getInstance();
        EnrolleeMarkRegister register;
        try {
            Optional<EnrolleeMarkRegister> optionalRegister =
                    dao.findEnrolleeMarkRegisterByEnrolleeId(enrolleeId);
            if (optionalRegister.isPresent()) {
                register = optionalRegister.get();
                register = calculateEnrolleeAverageMark(register);
                return Optional.of(register);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding register", e);
            throw new ServiceException(e);
        }
    }

    public Optional<EnrolleeMarkRegister> updateEnrolleeRegister(int enrolleeId, Map<String, String> parameters)
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
            if (registerDao.update(register, enrolleeId)) {
                register = calculateEnrolleeAverageMark(register);
                return Optional.of(register);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in updating register", e);
            throw new ServiceException(e);
        }
    }

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

    private EnrolleeMarkRegister calculateEnrolleeAverageMark(EnrolleeMarkRegister register) {
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
