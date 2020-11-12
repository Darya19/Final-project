package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.UserDaoImpl;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.util.PasswordEncryptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserServiceImpl {

    public static EnrolleeMarkRegisterServiceImpl instance;
    private static Logger logger = LogManager.getLogger();

    public static EnrolleeMarkRegisterServiceImpl getInstance() {
        if (instance == null) {
            instance = new EnrolleeMarkRegisterServiceImpl();
        }
        return instance;
    }

    public boolean checkEmailAndPassword(String email, String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            Optional<String> optionalPassword = userDao.findPasswordByEmail(email);
            if (optionalPassword.isPresent()) {
                String foundPassword = optionalPassword.get();
                String hashPassword = PasswordEncryptor.encryptPassword(password);
                if (hashPassword.equals(foundPassword)) {
                    return true;
                }
            }
            return false;
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }
//login
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> foundUser = userDao.findUserByEmail(email);
            return foundUser;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
