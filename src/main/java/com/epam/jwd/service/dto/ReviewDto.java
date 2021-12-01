package com.epam.jwd.service.dto;

public class ReviewDto extends AbstractDto<Integer> {
    private Integer courseId;
    private Integer studentId;
    private int grade;
    private int attendance;
    private String review;

    public ReviewDto(){

    }

    public ReviewDto(Integer courseId, Integer studentId, int grade, int attendance, String review) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
        this.attendance = attendance;
        this.review = review;
    }

    public ReviewDto(Integer id, Integer courseId, Integer studentId, int grade, int attendance, String review) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
        this.attendance = attendance;
        this.review = review;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewDto reviewDto = (ReviewDto) o;

        if (grade != reviewDto.grade) return false;
        if (attendance != reviewDto.attendance) return false;
        if (!courseId.equals(reviewDto.courseId)) return false;
        if (!studentId.equals(reviewDto.studentId)) return false;
        return review.equals(reviewDto.review);
    }

    @Override
    public int hashCode() {
        int result = courseId.hashCode();
        result = 31 * result + studentId.hashCode();
        result = 31 * result + grade;
        result = 31 * result + attendance;
        result = 31 * result + review.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                ", grade=" + grade +
                ", attendance=" + attendance +
                ", review='" + review + '\'' +
                '}';
    }
}
