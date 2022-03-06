package com.abderrahmane.elearning.common.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "connection")
@IdClass(ConnectionId.class)
public class StudentTeacherConnection {
    @Id
    @Column(name = "teacher_id")
    private String teacherId;

    @Id
    @Column(name = "student_id")
    private String studentId;

    @ManyToOne(targetEntity = TeacherProfile.class, optional = false)
    @JoinColumn(name = "teacher_id")
    @MapsId("teacherId")
    private TeacherProfile teacher;

    @ManyToOne(targetEntity = StudentProfile.class, optional = false)
    @JoinColumn(name = "student_id")
    @MapsId("studentId")
    private StudentProfile student;

    public StudentTeacherConnection () {}

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public StudentProfile getStudentProfile() {
        return this.student;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.student = studentProfile;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public TeacherProfile getTeacherProfile() {
        return this.teacher;
    }

    public void setTeacherProfile(TeacherProfile teacherProfile) {
        this.teacher = teacherProfile;
    }
}
