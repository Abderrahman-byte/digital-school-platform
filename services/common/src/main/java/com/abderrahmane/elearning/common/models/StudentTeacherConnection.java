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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "connection")
@IdClass(ConnectionId.class)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
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

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdDate = Calendar.getInstance();
}
