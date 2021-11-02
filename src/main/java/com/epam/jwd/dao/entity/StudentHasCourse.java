package com.epam.jwd.dao.entity;

import java.util.Date;

public class StudentHasCourse extends Entity<Integer> {
    private Course course;
    private Account account;
    private Date applicationDate;

    public StudentHasCourse(Integer id, Course course, Account account, Date applicationDate) {
        this.id = id;
        this.course = course;
        this.account = account;
        this.applicationDate = applicationDate;
    }

    public StudentHasCourse(Course course, Account account, Date applicationDate) {
        this.course = course;
        this.account = account;
        this.applicationDate = applicationDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentHasCourse that = (StudentHasCourse) o;

        if (!course.equals(that.course)) return false;
        if (!account.equals(that.account)) return false;
        return applicationDate.equals(that.applicationDate);
    }

    @Override
    public int hashCode() {
        int result = course.hashCode();
        result = 31 * result + account.hashCode();
        result = 31 * result + applicationDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StudentHasCourse{" +
                "id=" + id +
                ", course=" + course +
                ", account=" + account +
                ", applicationDate=" + applicationDate +
                '}';
    }
}
