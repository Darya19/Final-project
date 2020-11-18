package com.epam.enrollee.model.entity;

/**
 * The type Passport.
 */
public class Passport {

    private int passportId;
    private String passportSeriesAndNumber;
    private String personalNumber;

    /**
     * Gets passport id.
     *
     * @return the passport id
     */
    public int getPassportId() {
        return passportId;
    }

    /**
     * Sets passport id.
     *
     * @param passportId the passport id
     */
    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    /**
     * Gets passport series and number.
     *
     * @return the passport series and number
     */
    public String getPassportSeriesAndNumber() {
        return passportSeriesAndNumber;
    }

    /**
     * Sets passport series and number.
     *
     * @param passportSeriesAndNumber the passport series and number
     */
    public void setPassportSeriesAndNumber(String passportSeriesAndNumber) {
        this.passportSeriesAndNumber = passportSeriesAndNumber;
    }

    /**
     * Gets personal number.
     *
     * @return the personal number
     */
    public String getPersonalNumber() {
        return personalNumber;
    }

    /**
     * Sets personal number.
     *
     * @param personalNumber the personal number
     */
    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passport passport = (Passport) o;

        if (getPassportId() != passport.getPassportId()) return false;
        if (getPassportSeriesAndNumber() != null ? !getPassportSeriesAndNumber().equals(passport.getPassportSeriesAndNumber()) : passport.getPassportSeriesAndNumber() != null)
            return false;
        return getPersonalNumber() != null ? getPersonalNumber().equals(passport.getPersonalNumber()) : passport.getPersonalNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = getPassportId();
        result = 31 * result + (getPassportSeriesAndNumber() != null ? getPassportSeriesAndNumber().hashCode() : 0);
        result = 31 * result + (getPersonalNumber() != null ? getPersonalNumber().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Passport{");
        sb.append("passportId=").append(passportId);
        sb.append(", passportSeriesAndNumber='").append(passportSeriesAndNumber).append('\'');
        sb.append(", personalNumber='").append(personalNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
