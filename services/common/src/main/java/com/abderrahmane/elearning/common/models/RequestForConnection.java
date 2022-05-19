package com.abderrahmane.elearning.common.models;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "request_for_connection")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class RequestForConnection {
    @Id
    private String id;

    @ManyToOne(targetEntity = TeacherProfile.class, optional = false)
    @JoinColumn(name = "teacher_id")
    private TeacherProfile teacherProfile;
    
    @ManyToOne(targetEntity = StudentProfile.class, optional = false)
    @JoinColumn(name = "student_id")
    private StudentProfile studentProfile;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdDate = Calendar.getInstance();
}
