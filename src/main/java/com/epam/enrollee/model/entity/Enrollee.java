package com.epam.enrollee.model.entity;

import com.epam.enrollee.model.enumtype.ApplicationStatus;

public class Enrollee extends User {

    private String userName;
    private String lastName;
    private String identificationNumber;
    private ApplicationStatus applicationStatus;

    public Enrollee() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = super.equals(o);
        if (!result) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Enrollee enrollee = (Enrollee) o;

        if (getUserName() != null ? !getUserName().equals(enrollee.getUserName()) : enrollee.getUserName() != null) return false;
        if (getLastName() != null ? !getLastName().equals(enrollee.getLastName()) : enrollee.getLastName() != null)
            return false;
        return getIdentificationNumber() != null ? getIdentificationNumber().equals(enrollee.getIdentificationNumber()) : enrollee.getIdentificationNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getIdentificationNumber() != null ? getIdentificationNumber().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        super.toString();
        final StringBuilder sb = new StringBuilder();
        sb.append("name='").append(userName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", identificationNumber='").append(identificationNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}


