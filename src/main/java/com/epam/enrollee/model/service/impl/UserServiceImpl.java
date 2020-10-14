package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.UserDaoException;
import com.epam.enrollee.exception.UserServiceException;
import com.epam.enrollee.model.dao.impl.UserDaoImpl;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.model.service.BaseUserService;
import com.epam.enrollee.util.PasswordEncryptor;
import com.google.protobuf.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements BaseUserService {

    private Logger logger = LogManager.getLogger();

    @Override
    public int createUser(User user) throws UserServiceException {
        return 0;
    }

    @Override
    public int removeUser(User user) throws UserServiceException {
        return 0;
    }

    @Override
    public Optional findUserByEmail(String email) throws UserServiceException {
        return Optional.empty();
    }

    @Override
    public Optional findUserByRole(String role) throws UserServiceException {
        return Optional.empty();
    }

    @Override
    public Optional findUserByStatus(String Status) throws UserServiceException {
        return Optional.empty();
    }


    @Override
    public Optional findUserById(int id) throws UserServiceException {
        return Optional.empty();
    }

    @Override
    public List findAll() throws UserServiceException {
        return null;
    }

    @Override
    public List<User> update(User user) throws UserServiceException {
        return null;
    }

    public boolean checkEmailAndPassword(String email, String password) throws ServiceException {
        UserDaoImpl dao = new UserDaoImpl();
        try {
            String foundPassword = dao.findPasswordByEmail(email).get();
            if (!foundPassword.isEmpty()) {
                String hashPassword = PasswordEncryptor.encryptPassword(password);
                if (hashPassword.equals(foundPassword)) {
                    return true;
                }
            }
            return false;
        } catch (UserDaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(""); //TODO message in exc
        }
    }
}
