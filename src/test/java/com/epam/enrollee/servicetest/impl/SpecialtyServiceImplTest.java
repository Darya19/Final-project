package com.epam.enrollee.servicetest.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.SpecialtyDaoImpl;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import com.epam.enrollee.model.type.RecruitmentType;
import com.epam.enrollee.model.type.StatusType;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class SpecialtyServiceImplTest {

    SpecialtyDaoImpl specialtyDao;
    SpecialtyServiceImpl specialtyService;

    @BeforeClass
    public void setUp() {
        specialtyService = SpecialtyServiceImpl.getInstance();
        Mockito.mockStatic(SpecialtyDaoImpl.class);
        specialtyDao = Mockito.mock(SpecialtyDaoImpl.class);
        Mockito.when(SpecialtyDaoImpl.getInstance()).thenReturn(specialtyDao);
    }

    @Test
    public void findSpecialtyByIdPositiveTest() {
        String id = "1";
        Specialty specialty = new Specialty();
        specialty.setSpecialtyId(1);
        specialty.setSpecialtyName("Documentation");
        specialty.setSpecialtyStatus(StatusType.ACTIVE);
        specialty.setRecruitment(RecruitmentType.OPENED);
        specialty.setNumberOfSeats(5);
        try {
            Mockito.when(specialtyDao.findById(Mockito.anyInt())).thenReturn(Optional.of(specialty));
            Optional<Specialty> actual = specialtyService.findSpecialtyById(id);
            Optional<Specialty> expected = Optional.of(specialty);
            assertEquals(actual, expected);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }

    @Test(priority = 1, expectedExceptions = {ServiceException.class})
    public void findSpecialtyByIdNegativeExceptionTest() throws ServiceException, DaoException {
        String id = "52";
        Mockito.when(specialtyDao.findById(Mockito.anyInt())).thenThrow(DaoException.class);
        specialtyService.findSpecialtyById(id);
    }

    @Test(priority = 1)
    public void findSpecialtyByIdNegativeInputParameterTest() {
        String id = "jk";
        try {
            Optional<Specialty> actual = specialtyService.findSpecialtyById(id);
            assertEquals(actual, Optional.empty());
        } catch (ServiceException e) {
            fail();
        }
    }
}
