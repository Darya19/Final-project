package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.EnrolleeDaoImpl;
import com.epam.enrollee.model.dao.impl.FacultyDaoImpl;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Faculty;

import java.util.Optional;

public class FacultyServiceImpl {

    public Optional<Faculty> findFacultyByUserId(int userId) throws ServiceException {
        FacultyDaoImpl dao = new FacultyDaoImpl();
        try {
            Optional<Faculty> foundFaculty = dao.findFacultyByUserId(userId);
            return foundFaculty;
        } catch (DaoException e) {
            throw new ServiceException("");//TODO message
        }
    }
}
