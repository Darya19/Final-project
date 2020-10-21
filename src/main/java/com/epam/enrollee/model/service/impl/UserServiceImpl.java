package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.UserDaoImpl;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.model.service.BaseService;
import com.epam.enrollee.util.PasswordEncryptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements BaseService {

    UserDaoImpl dao = new UserDaoImpl();

    private Logger logger = LogManager.getLogger();

    @Override
    public int createUser(Enrollee user) throws ServiceException {
        return 0;
    }

    @Override
    public int removeUser(Enrollee user) throws ServiceException {
        return 0;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        try {
            Optional<User> foundUser = dao.findUserByEmail(email);
            return foundUser;
        } catch (DaoException e) {
            throw new ServiceException("");//TODO message
        }
    }

    @Override
    public Optional findUserByRole(String role) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Optional findUserByStatus(String Status) throws ServiceException {
        return Optional.empty();
    }


    @Override
    public Optional findUserById(int id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List findAll() throws ServiceException {
        return null;
    }

    @Override
    public List<Enrollee> update(Enrollee user) throws ServiceException {
        return null;
    }

    public boolean checkEmailAndPassword(String email, String password) throws ServiceException {
        try {
            String foundPassword = dao.findPasswordByEmail(email).get();
            if (!foundPassword.isEmpty()) {
                String hashPassword = PasswordEncryptor.encryptPassword(password);
                if (hashPassword.equals(foundPassword)) {
                    return true;
                }
            }
            return false;
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(""); //TODO message in exc
        }
    }
}
