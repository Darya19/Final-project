package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.EnrolleeMarkRegisterDao;
import com.epam.enrollee.model.dao.SubjectDao;
import com.epam.enrollee.model.dao.impl.EnrolleeMarkRegisterDaoImpl;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.EnrolleeMarkRegisterService;
import com.epam.enrollee.util.NumberParser;
import com.epam.enrollee.validator.ProjectValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

/**
 * The type Enrollee mark register service.
 * Class implements base service and enrollee mark register service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class EnrolleeMarkRegisterServiceImpl implements EnrolleeMarkRegisterService {

    /**
     * The constant instance.
     */
    public static EnrolleeMarkRegisterServiceImpl instance;
    private static Logger logger = LogManager.getLogger();

    /**
     * Gets instance.
     *
     * @return the instance
     */
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
        EnrolleeMarkRegisterDao dao = EnrolleeMarkRegisterDaoImpl.getInstance();
        try {
            Optional<EnrolleeMarkRegister> optionalRegister =
                    dao.findEnrolleeMarkRegisterByEnrolleeId(enrolleeId);
            optionalRegister.ifPresent(this::calculateEnrolleeAverageMark);
            return optionalRegister;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding register", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<EnrolleeMarkRegister> updateEnrolleeMarks(int enrolleeId, Map<String, String> parameters)
            throws ServiceException {
        EnrolleeMarkRegisterDao registerDao = EnrolleeMarkRegisterDaoImpl.getInstance();
        SubjectDao subjectDao = SubjectDaoImpl.getInstance();
        EnrolleeMarkRegister register = new EnrolleeMarkRegister();
        NumberParser parser = NumberParser.getInstance();
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
            logger.log(Level.ERROR, "Error in updating marks", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> checkParameters(Map<String, String> parameters) {
        ProjectValidator validator = ProjectValidator.getInstance();
        for (Map.Entry<String, String> pair : parameters.entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();
            if (!validator.isMarkValid(value)) {
                parameters.put(key, EMPTY_VALUE);
            }
        }
        return parameters;
    }

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