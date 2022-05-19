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
@Table(name = "teacher_school")
@IdClass(SchoolTeacherId.class)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
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
    private Calendar createdDate = Calendar.getInstance();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ended_date")
    private Calendar endedDate;
}
