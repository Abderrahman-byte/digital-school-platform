package com.abderrahmane.elearning.common.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_profil")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentProfile {
    @Id
    @Column(name = "account_id")
    private String id;

    @MapsId
    @OneToOne(targetEntity = Account.class, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
    
    
    @Column(name = "first_name", nullable = false)
    private String firstname;
    
    @Column(name = "last_name", nullable = false)
    private String lastname;
    
    @ManyToOne(targetEntity = City.class, optional = false)
    @JoinColumn(name = "location")
    private City location;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "day_of_birth", nullable = false)
    private Calendar dayOfBirth;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Calendar createdDate = Calendar.getInstance();

    @OneToMany(targetEntity = RequestForConnection.class, orphanRemoval = true, mappedBy = "studentProfile")
    private List<RequestForConnection> requests = new ArrayList<>();

    @OneToMany(targetEntity = StudentTeacherConnection.class, orphanRemoval = true, mappedBy = "student")
    private List<StudentTeacherConnection> connections = new ArrayList<>();
}
