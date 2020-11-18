package com.epam.enrollee.servicetest.impl;

import com.epam.enrollee.dataprovider.StaticDataProvider;
import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.EnrolleeMarkRegisterDaoImpl;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class EnrolleeMarkRegisterServiceImplTest {
    EnrolleeMarkRegisterDaoImpl registerDao;
    EnrolleeMarkRegisterServiceImpl registerService;

    @BeforeClass
    public void setUp() {
        registerService = EnrolleeMarkRegisterServiceImpl.getInstance();
        Mockito.mockStatic(EnrolleeMarkRegisterDaoImpl.class);
        registerDao = Mockito.mock(EnrolleeMarkRegisterDaoImpl.class);
        Mockito.when(EnrolleeMarkRegisterDaoImpl.getInstance()).thenReturn(registerDao);
    }

    @Test(dataProvider = "all subjects", dataProviderClass = StaticDataProvider.class)
    public void findEnrolleeMarkRegisterPositiveTest(List<Subject> subjects) {
        int id = 1;
        int count = 1;
        EnrolleeMarkRegister register = new EnrolleeMarkRegister();
        for (Subject subject : subjects) {
            register.put(subject, count);
            count++;
        }
        Optional<EnrolleeMarkRegister> optionalRegister = Optional.of(register);
        try {
            Mockito.when(registerDao.findEnrolleeMarkRegisterByEnrolleeId(Mockito.anyInt())).thenReturn(optionalRegister);
            Optional<EnrolleeMarkRegister> actual = registerService.findEnrolleeMarkRegister(id);
            assertEquals(actual, optionalRegister);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }

    @Test(priority = 1, expectedExceptions = {ServiceException.class})
    public void findEnrolleeByEmailNegativeExceptionTest() throws ServiceException, DaoException {
        int id = 1;
        Mockito.when(registerDao.findEnrolleeMarkRegisterByEnrolleeId(Mockito.anyInt())).thenThrow(DaoException.class);
        registerService.findEnrolleeMarkRegister(id);
    }

    @Test()
    public void findEnrolleeByEmailNegativeTest() {
        int id = 1;
        try {
            Mockito.when(registerDao.findEnrolleeMarkRegisterByEnrolleeId(Mockito.anyInt())).thenReturn(Optional.empty());
            Optional<EnrolleeMarkRegister> actual = registerService.findEnrolleeMarkRegister(id);
            assertEquals(actual, Optional.empty());
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }
}
