package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Subject;

import java.util.List;

public interface SubjectService extends BaseService {

    List<Subject> findAllSubjects() throws ServiceException;
}
