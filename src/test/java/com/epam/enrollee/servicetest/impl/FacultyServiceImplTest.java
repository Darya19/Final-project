package com.epam.enrollee.servicetest.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.FacultyDaoImpl;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class FacultyServiceImplTest {

    FacultyDaoImpl facultyDao;
    FacultyServiceImpl facultyService;

    @BeforeClass
    public void setUp() {
        facultyService = FacultyServiceImpl.getInstance();
        Mockito.mockStatic(FacultyDaoImpl.class);
        facultyDao = Mockito.mock(FacultyDaoImpl.class);
        Mockito.when(FacultyDaoImpl.getInstance()).thenReturn(facultyDao);
    }

    @Test
    public void checkConsideredApplicationsTrueTest() {
        String id = "1";
        List<Integer> foundEnrolleeId = new ArrayList<>();
        foundEnrolleeId.add(1);
        foundEnrolleeId.add(2);
        try {
            Mockito.when(facultyDao.findConsideredEnrolleeIdById(Mockito.anyInt())).thenReturn(foundEnrolleeId);
            boolean actual = facultyService.checkConsideredApplications(id);
            assertTrue(actual);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }

    @Test(priority = 1, expectedExceptions = {ServiceException.class})
    public void checkConsideredApplicationsNegativeTest() throws ServiceException, DaoException {
        String id = "52";
        Mockito.when(facultyDao.findConsideredEnrolleeIdById(Mockito.anyInt())).thenThrow(DaoException.class);
        facultyService.checkConsideredApplications(id);
    }

    @Test(priority = 1)
    public void checkConsideredApplicationsFalseTest() {
        String id = "jk";
        try {
            Mockito.when(facultyDao.findConsideredEnrolleeIdById(Mockito.anyInt())).thenReturn(new ArrayList<>());
            boolean actual = facultyService.checkConsideredApplications(id);
            assertFalse(actual);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }
}
