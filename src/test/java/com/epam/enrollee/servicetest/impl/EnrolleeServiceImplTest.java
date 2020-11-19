package com.epam.enrollee.servicetest.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.EnrolleeDaoImpl;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.type.ApplicationStatus;
import com.epam.enrollee.model.type.RoleType;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class EnrolleeServiceImplTest {

    EnrolleeDaoImpl enrolleeDao;
    EnrolleeServiceImpl enrolleeService;

    @BeforeClass
    public void setUp() {
        enrolleeService = EnrolleeServiceImpl.getInstance();
        Mockito.mockStatic(EnrolleeDaoImpl.class);
        enrolleeDao = Mockito.mock(EnrolleeDaoImpl.class);
        Mockito.when(EnrolleeDaoImpl.getInstance()).thenReturn(enrolleeDao);
    }

    @Test
    public void findEnrolleeByEmailPositiveTest() {
        String id = "1";
        Enrollee enrollee = new Enrollee();
        enrollee.setUserId(1);
        enrollee.setEmail("gda@mail.ru");
        enrollee.setRole(RoleType.USER);
        enrollee.setFirstName("Ivan");
        enrollee.setMiddleName("Ivanovich");
        enrollee.setLastName("Ivanov");
        enrollee.setApplicationStatus(ApplicationStatus.CONSIDERED);
        enrollee.setChosenSpecialtyId(1);
        enrollee.setChosenFacultyId(2);
        Optional<Enrollee> optionalEnrollee = Optional.of(enrollee);
        try {
            Mockito.when(enrolleeDao.findEnrolleeByEmail(Mockito.any())).thenReturn(optionalEnrollee);
            Optional<Enrollee> actual = enrolleeService.findEnrolleeByEmail(id);
            assertEquals(actual, optionalEnrollee);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }

    @Test(priority = 1, expectedExceptions = {ServiceException.class})
    public void findEnrolleeByEmailNegativeExceptionTest() throws ServiceException, DaoException {
        String email = "gda@mail.ru";
        Mockito.when(enrolleeDao.findEnrolleeByEmail(Mockito.any())).thenThrow(DaoException.class);
        enrolleeService.findEnrolleeByEmail(email);
    }

    @Test()
    public void findEnrolleeByEmailNegativeTest() {
        String id = "1";
        try {
            Mockito.when(enrolleeDao.findEnrolleeByEmail(Mockito.any())).thenReturn(Optional.empty());
            Optional<Enrollee> actual = enrolleeService.findEnrolleeByEmail(id);
            assertEquals(actual, Optional.empty());
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }
}