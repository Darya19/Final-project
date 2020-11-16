package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.UserDao;
import com.epam.enrollee.model.dao.impl.UserDaoImpl;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.model.service.UserService;
import com.epam.enrollee.util.PasswordEncryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    public static UserServiceImpl instance;
    private static Logger logger = LogManager.getLogger();

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        throw new UnsupportedOperationException("Impossible add user");
    }

    @Override
    public Map<String, String> checkParameters(Map<String, String> parameters) throws ServiceException {
        throw new UnsupportedOperationException("Impossible check all parameters");
    }

    @Override
    public boolean checkEmailAndPassword(String email, String password) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
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
            logger.log(Level.ERROR, "Error in checking email and password", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> foundUser = userDao.findUserByEmail(email);
            return foundUser;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding user by email", e);
            throw new ServiceException(e);
        }
    }
}
