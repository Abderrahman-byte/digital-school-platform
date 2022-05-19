package com.abderrahmane.elearning.common.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

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
@Table(name = "school_profil")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class SchoolProfile {
    @Id
    @Column(name = "account_id")
    private String id;

    @MapsId
    @OneToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id")
    private Account account;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private String subtitle;
    
    @Column
    private String overview;

    @OneToMany(targetEntity = SchoolTeacher.class, mappedBy = "school", orphanRemoval = true)
    private List<SchoolTeacher> teachers = new ArrayList<>();

    @ManyToOne(targetEntity = City.class, optional = false)
    @JoinColumn(name = "location")
    private City location;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Calendar createdDate = Calendar.getInstance();
}
