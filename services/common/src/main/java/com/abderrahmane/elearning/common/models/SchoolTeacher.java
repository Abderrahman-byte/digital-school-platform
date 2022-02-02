package com.abderrahmane.elearning.common.models;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "teacher_school")
@IdClass(SchoolTeacherId.class)
public class SchoolTeacher {
    @Id
    @Column(name = "school_id")
    private String schoolId;
    
    @Id
    @Column(name = "teacher_id")
    private String teacherId;

    @ManyToOne(targetEntity = TeacherProfile.class, optional = false)
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id")
    private TeacherProfile teacher;
    
    @ManyToOne(targetEntity = SchoolProfile.class, optional = false)
    @MapsId("schoolId")
    @JoinColumn(name = "school_id")
    private SchoolProfile school;

    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private boolean verified = false;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "created_date")
    private Calendar createdDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ended_date")
    private Calendar endedDate;

    public SchoolTeacher() {
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TeacherProfile getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherProfile teacher) {
        this.teacher = teacher;
    }

    public SchoolProfile getSchool() {
        return school;
    }

    public void setSchool(SchoolProfile school) {
        this.school = school;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Calendar getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Calendar endedDate) {
        this.endedDate = endedDate;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }
}
