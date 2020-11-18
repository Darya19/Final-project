package com.epam.enrollee.model.entity;

/**
 * The type Subject.
 */
public class Subject {

    private int subjectId;
    private String subjectName;

    /**
     * Gets subject id.
     *
     * @return the subject id
     */
    public int getSubjectId() {
        return subjectId;
    }

    /**
     * Sets subject id.
     *
     * @param subjectId the subject id
     */
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Gets subject name.
     *
     * @return the subject name
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Sets subject name.
     *
     * @param subjectName the subject name
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (getSubjectId() != subject.getSubjectId()) return false;
        return getSubjectName() != null ? getSubjectName().equals(subject.getSubjectName()) : subject.getSubjectName() == null;
    }

    @Override
    public int hashCode() {
        int result = getSubjectId();
        result = 31 * result + (getSubjectName() != null ? getSubjectName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subject{");
        sb.append("subjectId=").append(subjectId);
        sb.append(", subjectName='").append(subjectName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
