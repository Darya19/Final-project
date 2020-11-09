package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.UserDaoImpl;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.util.PasswordEncryptor;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserServiceImpl {

    //login
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
