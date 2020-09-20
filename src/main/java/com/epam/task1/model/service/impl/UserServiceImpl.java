package com.epam.task1.model.service.impl;

import com.epam.task1.exception.UserDaoException;
import com.epam.task1.exception.UserServiceException;
import com.epam.task1.model.dao.impl.UserDaoImpl;
import com.epam.task1.model.entity.User;
import com.epam.task1.model.enumtype.Tag;
import com.epam.task1.model.service.BaseUserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements BaseUserService {
    @Override
    public int createUser(User user) throws UserServiceException {
        return 0;
    }

    @Override
    public int removeUser(User user) throws UserServiceException {
        return 0;
    }

    @Override
    public Optional getUser(Tag tag, String parameter) throws UserServiceException {
        return Optional.empty();
    }

    @Override
    public Optional getUserById(Tag tag, int parameter) throws UserServiceException {
        return Optional.empty();
    }

    @Override
    public List getAll() throws UserServiceException {
        return null;
    }

    public boolean checkLogin(String login, String password) {
        UserDaoImpl dao = new UserDaoImpl();
        try {
            List<User> users = dao.getAll();
            for(User user : users){
                if(user.getEmail().equals(login) && user.getPassword().equals(password)){
                    return true;
                }
            }
        } catch (UserDaoException e) {
            e.printStackTrace();//TODO unchecked exc log
        }
        return false;
    }
}
