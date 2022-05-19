package com.abderrahmane.elearning.common.models;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GenericGenerator(name = "random_string_generator", strategy = "com.abderrahmane.elearning.common.utils.RandomStringGenerator")
    @GeneratedValue(generator = "random_string_generator")
    private String id;
    
    @Column(name = "label", nullable = false)
    private String label;
    
    @ManyToOne(targetEntity = TeacherProfile.class, optional = false)
    @JoinColumn(name = "created_by")
    private TeacherProfile createdBy;
    
    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Calendar createdDate = Calendar.getInstance();
    
    @Column(name = "deleted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar deletedDate;
}
