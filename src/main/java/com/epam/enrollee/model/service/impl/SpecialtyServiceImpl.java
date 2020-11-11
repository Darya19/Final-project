package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.SpecialtyDaoImpl;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.BaseService;
import com.epam.enrollee.model.type.RecruitmentType;
import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.util.MapKeys;
import com.epam.enrollee.validator.ProjectValidator;

import java.util.*;

public class SpecialtyServiceImpl implements BaseService<Specialty> {

    private static final String EMPTY_STRING = "";

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        Map<String, Object> objectMap = new HashMap<>();
        int numberOfSeats = parser.parseToInt(parameters.get(MapKeys.NUMBER_OF_SEATS));
        int facultyId = parser.parseToInt(parameters.get(MapKeys.FACULTY_ID));
        objectMap.put(MapKeys.NUMBER_OF_SEATS, numberOfSeats);
        objectMap.put(MapKeys.SPECIALTY_NAME, parameters.get(MapKeys.SPECIALTY_NAME));
        objectMap.put(MapKeys.FACULTY_ID, facultyId);
        try {
            return specialtyDao.add(objectMap);
        } catch (DaoException e) {
            throw new ServiceException("Impossible add faculty", e);
        }
    }

    @Override
    public int remove(Specialty user) throws ServiceException {
        return 0;
    }

    public boolean remove(String specialtyId) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intSpecialtyId;
        if (validator.isIntParameterValid(specialtyId)) {
            try {
                intSpecialtyId = parser.parseToInt(specialtyId);
                return specialtyDao.updateStatusById(intSpecialtyId);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    @Override
    public Optional<Specialty> find(String value) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public boolean update(int value, Map<String, String> parameters) throws ServiceException {
        return false;
    }

    public List<Specialty> findAllOpenSpecialties() throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        try {
            List<Specialty> specialties = dao.findAll();
            return specialties;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    //login register
    public Optional<Specialty> findEnrolleeSpecialty(int enrolleeId) throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        try {
            Optional<Specialty> specialty = dao.findSpecialtyByErolleeId(enrolleeId);
            return specialty;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public boolean update(Map<String, String> parameters) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        Map<String, Object> objectParameters = new HashMap<>();
        int intSpecialtyId;
        int number;
        try {
            intSpecialtyId = parser.parseToInt(parameters.get(MapKeys.SPECIALTY_ID));
            number = parser.parseToInt(parameters.get(MapKeys.NUMBER_OF_SEATS));
            objectParameters.put(MapKeys.SPECIALTY_ID, intSpecialtyId);
            objectParameters.put(MapKeys.SPECIALTY_NAME, parameters.get(MapKeys.SPECIALTY_NAME));
            objectParameters.put(MapKeys.NUMBER_OF_SEATS, number);
            return specialtyDao.update(objectParameters);
        } catch (DaoException e) {
            throw new ServiceException("Impossible add faculty", e);
        }
    }

    //update sp toeditprofile
    public List<Specialty> findOpenSpecialtiesOfFaculty(String facultyId) throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        List<Specialty> specialties = null;
        int intFacultyId;
        if (validator.isIntParameterValid(facultyId)) {
            intFacultyId = parser.parseToInt(facultyId);
            try {
                specialties = dao.findOpenSpecialtiesListByFacultyId(intFacultyId);
                return specialties;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    public List<Specialty> findActiveSpecialtiesOfFaculty(String facultyId) throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        List<Specialty> specialties = null;
        int intFacultyId;
        if (validator.isIntParameterValid(facultyId)) {
            intFacultyId = parser.parseToInt(facultyId);
            try {
                specialties = dao.findActiveSpecialtiesListByFacultyId(intFacultyId);
                return specialties;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    public Optional<Specialty> findSpecialtyById(String specialtyId) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intSpecialtyId;
        if (validator.isIntParameterValid(specialtyId)) {
            intSpecialtyId = parser.parseToInt(specialtyId);
            try {
                Optional<Specialty> specialty = specialtyDao.findById(intSpecialtyId);
                return specialty;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            return Optional.empty();
        }
    }

    public boolean changeSpecialtyRecruitment(String specialtyId, String recruitment)
            throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intSpecialtyId;
        boolean isChanged;
        if (validator.isIntParameterValid(specialtyId)
                && validator.isStringParameterValid(recruitment)) {
            try {
                intSpecialtyId = parser.parseToInt(specialtyId);
                if (recruitment.equalsIgnoreCase(RecruitmentType.OPENED.getType())) {
                    isChanged = specialtyDao.updateRecruitmentBySpecialtyId(intSpecialtyId,
                            RecruitmentType.CLOSED.getType());
                } else {
                    isChanged = specialtyDao.updateRecruitmentBySpecialtyId(intSpecialtyId,
                            RecruitmentType.OPENED.getType());
                }
                return isChanged;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    public boolean checkApplications(String specialtyId) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intSpecialtyId;
        if (validator.isIntParameterValid(specialtyId)) {
            try {
                intSpecialtyId = parser.parseToInt(specialtyId);
                List<Integer> foundEnrolleeId = specialtyDao.findEnrolleeIdBySpecialtyId(intSpecialtyId);
                return foundEnrolleeId.size() > 0;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    public Map<String, String> checkParameters(Map<String, String> parameters) {
        ProjectValidator validator = new ProjectValidator();
        boolean isNameValid;
        boolean isNumberValid;
        if (parameters.get(MapKeys.SPECIALTY_ID) != null) {
            boolean isSpecialtyIdValid;
            isSpecialtyIdValid = validator.isIntParameterValid(parameters.get(MapKeys.SPECIALTY_ID));
            if (!isSpecialtyIdValid) {
                parameters.put(MapKeys.SPECIALTY_ID, EMPTY_STRING);
            }
        }
        isNameValid = validator.isStringParameterValid(parameters.get(MapKeys.SPECIALTY_NAME));
        isNumberValid = validator.isIntParameterValid(parameters.get(MapKeys.NUMBER_OF_SEATS));
        if (!isNameValid) {
            parameters.put(MapKeys.SPECIALTY_NAME, EMPTY_STRING);
        }
        if (!isNumberValid) {
            parameters.put(MapKeys.NUMBER_OF_SEATS, EMPTY_STRING);
        }
        return parameters;
    }
}
