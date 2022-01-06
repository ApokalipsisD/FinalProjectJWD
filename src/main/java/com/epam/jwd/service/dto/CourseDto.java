package com.epam.jwd.service.dto;

import com.epam.jwd.dao.entity.Status;

import java.sql.Date;
import java.util.Objects;

public class CourseDto extends AbstractDto<Integer> {
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status courseStatus;
    private Integer teacherId;

    public CourseDto() {

    }

    public CourseDto(String title, String description, Date startDate, Date endDate, Integer courseStatus, Integer teacherId){
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = Status.getById(courseStatus);
        this.teacherId = teacherId;
    }

    public CourseDto(Integer id, String title, String description, Date startDate, Date endDate, Integer courseStatus, Integer teacherId){
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = Status.getById(courseStatus);
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

    public Status getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(Status courseStatus) {
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
        return title.equals(courseDto.title) && description.equals(courseDto.description) && startDate.equals(courseDto.startDate) && endDate.equals(courseDto.endDate) && courseStatus == courseDto.courseStatus && teacherId.equals(courseDto.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, startDate, endDate, courseStatus, teacherId);
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
                ", teacher=" + teacherId +
                '}';
    }
}
