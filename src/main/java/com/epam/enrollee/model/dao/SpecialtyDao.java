package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.Specialty;

import java.util.List;
import java.util.Optional;

/**
 * The interface Specialty dao.
 */
public interface SpecialtyDao extends UniversityDao<Specialty> {

    /**
     * Find faculty id by specialty id optional.
     *
     * @param specialtyId the specialty id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Integer> findFacultyIdBySpecialtyId(int specialtyId) throws DaoException;

    /**
     * Find open specialties list by faculty id list.
     *
     * @param facultyId the faculty id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Specialty> findOpenSpecialtiesListByFacultyId(int facultyId) throws DaoException;

    /**
     * Find active specialties list by faculty id list.
     *
     * @param facultyId the faculty id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Specialty> findActiveSpecialtiesListByFacultyId(int facultyId) throws DaoException;

    /**
     * Update opened recruitment by specialty id boolean.
     *
     * @param specialtyId  the specialty id
     * @param applications the applications
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateOpenedRecruitmentBySpecialtyId(int specialtyId, List<Integer> applications) throws DaoException;

    /**
     * Update closed recruitment by specialty id boolean.
     *
     * @param specialtyId the specialty id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateClosedRecruitmentBySpecialtyId(int specialtyId) throws DaoException;

    /**
     * Find all enrollee with accepted application status int.
     *
     * @param specialtyId the specialty id
     * @return the int
     * @throws DaoException the dao exception
     */
    int findAllEnrolleeWithAcceptedApplicationStatus(int specialtyId) throws DaoException;

    /**
     * Find all unarchived enrollee by specialty id list.
     *
     * @param specialtyId the specialty id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Integer> findAllUnarchivedEnrolleeBySpecialtyId(int specialtyId) throws DaoException;
}
