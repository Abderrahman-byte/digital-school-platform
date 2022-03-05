package com.abderrahmane.elearning.common.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "request_for_connection")
public class RequestForConnection {
    @Id
    private String id;

    @ManyToOne(targetEntity = TeacherProfile.class, optional = false)
    @JoinColumn(name = "teacher_id")
    private TeacherProfile teacherProfile;
    
    @ManyToOne(targetEntity = StudentProfile.class, optional = false)
    @JoinColumn(name = "student_id")
    private StudentProfile studentProfile;


    public RequestForConnection () {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public TeacherProfile getTeacherProfile() {
        return teacherProfile;
    }

    public void setTeacherProfile(TeacherProfile teacherProfile) {
        this.teacherProfile = teacherProfile;
    }
}
