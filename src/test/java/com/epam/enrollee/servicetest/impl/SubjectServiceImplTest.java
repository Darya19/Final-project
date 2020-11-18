package com.epam.enrollee.servicetest.impl;

import com.epam.enrollee.dataprovider.StaticDataProvider;
import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.impl.SubjectServiceImpl;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class SubjectServiceImplTest {

    SubjectDaoImpl subjectDao;
    SubjectServiceImpl subjectService;

    @BeforeClass
    public void setUp() {
        subjectService = SubjectServiceImpl.getInstance();
        Mockito.mockStatic(SubjectDaoImpl.class);
        subjectDao = Mockito.mock(SubjectDaoImpl.class);
        Mockito.when(SubjectDaoImpl.getInstance()).thenReturn(subjectDao);
    }

    @Test(dataProvider = "all subjects", dataProviderClass = StaticDataProvider.class)
    public void findAllSubjectsPositiveTest(List<Subject> subjects) {
        try {
            Mockito.when(subjectDao.findAll()).thenReturn(subjects);
            List<Subject> actual = subjectService.findAllSubjects();
            assertEquals(actual, subjects);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }

    @Test(expectedExceptions = {ServiceException.class})
    public void findAllSubjectsNegativeTest() throws ServiceException, DaoException {
        Mockito.when(subjectDao.findAll()).thenThrow(DaoException.class);
        subjectService.findAllSubjects();
    }
}
