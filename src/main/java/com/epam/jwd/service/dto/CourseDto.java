package com.epam.jwd.service.dto;

import java.sql.Date;

public class CourseDto extends AbstractDto<Integer> {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer courseStatus;
    private Integer teacherId;

    public CourseDto() {

    }

    public CourseDto(String title, String description, Date startDate, Date endDate, Integer courseStatus, Integer teacherId) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.teacherId = teacherId;
    }

    public CourseDto(Integer id, String title, String description, Date startDate, Date endDate, Integer courseStatus, Integer teacherId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.teacherId = teacherId;
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

    public Integer getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Integer courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseDto courseDto = (CourseDto) o;

        if (!title.equals(courseDto.title)) return false;
        if (!description.equals(courseDto.description)) return false;
        if (!startDate.equals(courseDto.startDate)) return false;
        if (!endDate.equals(courseDto.endDate)) return false;
        if (!courseStatus.equals(courseDto.courseStatus)) return false;
        return teacherId.equals(courseDto.teacherId);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + courseStatus.hashCode();
        result = 31 * result + teacherId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", courseStatus=" + courseStatus +
                ", teacherId=" + teacherId +
                '}';
    }
}
