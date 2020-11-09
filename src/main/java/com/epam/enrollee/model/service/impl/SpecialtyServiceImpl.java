package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.SpecialtyDaoImpl;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpecialtyServiceImpl implements BaseService<Specialty> {

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        return false;
    }

    @Override
    public int remove(Specialty user) throws ServiceException {
        return 0;
    }

    @Override
    public Optional<Specialty> find(String value) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<Specialty> findAll() throws ServiceException {
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

    @Override
    public boolean update(int value, Map<String, String> parameters) throws ServiceException {
        return false;
    }
//update sp toeditprofile
    public List<Specialty> findOpenSpecialtiesOfFaculty(int facultyId) throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        try{
            List<Specialty> specialties = dao.findOpenSpecialtiesListByFacultyId(facultyId);
            return specialties;}
        catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    public List<Specialty> findAllSpecialtiesOfFaculty(int facultyId) throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        try{
            List<Specialty> specialties = dao.findAllSpecialtiesListByFacultyId(facultyId);
            return specialties;}
        catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    public Optional<Specialty> findSpecialtyById(int specialtyId) throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        try {
            Optional<Specialty> specialty = dao.findById(specialtyId);
            return specialty;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
