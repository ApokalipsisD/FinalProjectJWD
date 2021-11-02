package com.epam.jwd.dao.entity;

import java.util.Date;

public class Course extends Entity<Integer> {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status courseStatus;
    private Account account;

    public Course(Integer id, String title, String description, Date startDate, Date endDate, Status courseStatus, Account account) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.account = account;
    }

    public Course(String title, String description, Date startDate, Date endDate, Status courseStatus, Account account) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.account = account;
    }

    public Course() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Status getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Status courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (!title.equals(course.title)) return false;
        if (!description.equals(course.description)) return false;
        if (!startDate.equals(course.startDate)) return false;
        if (!endDate.equals(course.endDate)) return false;
        if (courseStatus != course.courseStatus) return false;
        return account.equals(course.account);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + courseStatus.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", courseStatus=" + courseStatus +
                ", account=" + account +
                '}';
    }
}
