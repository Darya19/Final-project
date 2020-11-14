package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.User;

import java.util.Optional;

public interface UserService extends BaseService {

    boolean checkEmailAndPassword(String email, String password) throws ServiceException;

    Optional<User> findUserByEmail(String email) throws ServiceException;
}
