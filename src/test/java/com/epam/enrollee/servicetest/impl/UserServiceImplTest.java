package com.epam.enrollee.servicetest.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.UserDaoImpl;
import com.epam.enrollee.model.service.impl.UserServiceImpl;
import com.epam.enrollee.util.PasswordEncryptor;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.testng.Assert.*;

public class UserServiceImplTest {

    UserDaoImpl userDao;
    UserServiceImpl userService;

    @BeforeClass
    public void setUp() {
        userService = UserServiceImpl.getInstance();
        Mockito.mockStatic(UserDaoImpl.class);
        userDao = Mockito.mock(UserDaoImpl.class);
        Mockito.when(UserDaoImpl.getInstance()).thenReturn(userDao);
    }

    @Test
    public void checkEmailAndPasswordTrueTest() {
        String password = "12345678";
        String email = "dasha@mail.ru";
        try {
            String hashPassword = PasswordEncryptor.encryptPassword(password);
            Mockito.when(userDao.findPasswordByEmail(Mockito.any())).thenReturn(Optional.of(hashPassword));
            boolean result = userService.checkEmailAndPassword(email, password);
            assertTrue(result);
        } catch (DaoException | ServiceException | NoSuchAlgorithmException e) {
            fail();
        }
    }

    @Test
    public void checkEmailAndPasswordFalseTest() {
        String password = "12345678";
        String email = "dasha@mail.ru";
        try {
            Mockito.when(userDao.findPasswordByEmail(Mockito.any())).thenReturn(Optional.empty());
            boolean result = userService.checkEmailAndPassword(email, password);
            assertFalse(result);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }

    @Test(expectedExceptions = {ServiceException.class})
    public void checkEmailAndPasswordNegativeTest() throws ServiceException, DaoException {
        String password = "12345678";
        String email = "dasha@mail.ru";
        Mockito.when(userDao.findPasswordByEmail(Mockito.any())).thenThrow(DaoException.class);
        userService.checkEmailAndPassword(email, password);
    }
}