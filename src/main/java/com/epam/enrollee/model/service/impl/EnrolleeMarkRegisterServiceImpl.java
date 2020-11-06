package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.EnrolleeMarkRegisterDaoImpl;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.BaseService;
import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.validator.EnrolleeValidator;
import sun.applet.resources.MsgAppletViewer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnrolleeMarkRegisterServiceImpl implements BaseService<EnrolleeMarkRegister> {

    private static final String EMPTY_VALUE = "";

    @Override
    public Optional<EnrolleeMarkRegister> create(Map<String, String> parameters) throws ServiceException {
        return Optional.empty();
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
    public List<EnrolleeMarkRegister> findAll() throws ServiceException {
        return null;
    }

    @Override
    public List<EnrolleeMarkRegister> update(EnrolleeMarkRegister value) throws ServiceException {
        return null;
    }

    public Optional<EnrolleeMarkRegister> findEnrolleeMarkRegister(int enrolleeId) throws ServiceException {
        EnrolleeMarkRegisterDaoImpl dao = EnrolleeMarkRegisterDaoImpl.getInstance();
        try {
            return dao.findEnrolleeMarkRegisterByEnrolleeId(enrolleeId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<EnrolleeMarkRegister> updateEnrolleRegister
            (int enrolleeId, Map<String, String> parameters) throws ServiceException {
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
                subject.ifPresent(subject1 -> register.put(subject1, markValue));
            }
            if(registerDao.update(register, enrolleeId)) {
                return Optional.of(register);
            }else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public  Map<String, String> checkMarks(Map<String, String> parameters){
        EnrolleeValidator validator = new EnrolleeValidator();
        for(Map.Entry<String, String> pair : parameters.entrySet()){
            String key = pair.getKey();
            String value = pair.getValue();
            if(!validator.isMarkValid(value)){
               parameters.put(key, EMPTY_VALUE);
            }
        }
        return parameters;
    }

    public EnrolleeMarkRegister calculateEnrolleeAverageMark(EnrolleeMarkRegister register){
     Map<Subject, Integer> testsResults = register.getTestsSubjectsAndMarks();
     int averageMark = 0;
     for(Integer mark : testsResults.values()){
         averageMark += mark;
     }
     register.setAverageMark(averageMark);
     return register;
    }
}
