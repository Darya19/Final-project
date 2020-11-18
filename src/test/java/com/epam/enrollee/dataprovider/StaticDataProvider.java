package com.epam.enrollee.dataprovider;

import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.util.MapKeys;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticDataProvider {

    @DataProvider(name = "correct parameters")
    public static Object[] correctParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(MapKeys.FIRST_NAME, "Ekaterina");
        parameters.put(MapKeys.LAST_NAME, "Smirnova");
        parameters.put(MapKeys.MIDDLE_NAME, "Ivanovna");
        parameters.put(MapKeys.PASSPORT_SERIES_AND_NUMBER, "JK8563655");
        parameters.put(MapKeys.PERSONAL_NUMBER, "9654HT41U14586");
        parameters.put(MapKeys.FACULTY_ID, "1");
        parameters.put(MapKeys.SPECIALTY_ID, "2");
        parameters.put(MapKeys.SUBJECT_ID_1, "3");
        parameters.put(MapKeys.MARK_1, "45");
        parameters.put(MapKeys.SUBJECT_ID_2, "5");
        parameters.put(MapKeys.MARK_2, "59");
        parameters.put(MapKeys.SUBJECT_ID_3, "6");
        parameters.put(MapKeys.MARK_3, "79");
        parameters.put(MapKeys.SUBJECT_ID_4, "8");
        parameters.put(MapKeys.MARK_4, "74");
        parameters.put(MapKeys.PASSWORD, "123456789");
        parameters.put(MapKeys.REPEATED_PASSWORD, "54354335335444");
        parameters.put(MapKeys.EMAIL, "katy.pet@mail.ru");
        return new Object[]{parameters};
    }

    @DataProvider(name = "incorrect parameters")
    public static Object[] incorrectParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(MapKeys.FIRST_NAME, "15");
        parameters.put(MapKeys.LAST_NAME, "1456");
        parameters.put(MapKeys.MIDDLE_NAME, "/'/");
        parameters.put(MapKeys.PASSPORT_SERIES_AND_NUMBER, "MC589");
        parameters.put(MapKeys.PERSONAL_NUMBER, "1289654HT");
        parameters.put(MapKeys.FACULTY_ID, "1");
        parameters.put(MapKeys.SPECIALTY_ID, "");
        parameters.put(MapKeys.SUBJECT_ID_1, "3");
        parameters.put(MapKeys.MARK_1, "45");
        parameters.put(MapKeys.SUBJECT_ID_2, "5");
        parameters.put(MapKeys.MARK_2, "59");
        parameters.put(MapKeys.SUBJECT_ID_3, "6");
        parameters.put(MapKeys.MARK_3, "79");
        parameters.put(MapKeys.SUBJECT_ID_4, "8");
        parameters.put(MapKeys.MARK_4, "74");
        parameters.put(MapKeys.PASSWORD, "123gh678");
        parameters.put(MapKeys.REPEATED_PASSWORD, "12345678");
        parameters.put(MapKeys.EMAIL, "katyamail.ru");
        return new Object[]{parameters};
    }

    @DataProvider(name = "all subjects")
    public static Object[] fundAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        Subject physics = new Subject();
        physics.setSubjectId(1);
        physics.setSubjectName("Physics");
        subjects.add(physics);
        Subject mats = new Subject();
        mats.setSubjectId(2);
        mats.setSubjectName("Mats");
        subjects.add(mats);
        return new Object[]{subjects};
    }
}
