package com.abderrahmane.elearning.common.models;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Account {
    @Id
    @GenericGenerator(name = "random_string_generator", strategy = "com.abderrahmane.elearning.common.utils.RandomStringGenerator")
    @GeneratedValue(generator = "random_string_generator")
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique =  true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name="is_admin", nullable = false)
    private boolean isAdmin = false;

    @Column(name="is_active", nullable = false)
    private boolean isActive = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "acc_type", nullable = false)
    @Type(type = "com.abderrahmane.elearning.common.helpers.EnumTypePostgreSql")
    private AccountType accountType = AccountType.STUDENT;

    @OneToOne(targetEntity = SchoolProfile.class, optional = true, mappedBy = "account", cascade = { CascadeType.ALL })
    private SchoolProfile schoolProfile;

    @OneToOne(targetEntity = TeacherProfile.class, optional = true, mappedBy = "account", cascade = { CascadeType.ALL })
    private TeacherProfile teacherProfile;

    @OneToOne(targetEntity = StudentProfile.class, optional = true, mappedBy = "account", cascade = { CascadeType.ALL })
    private StudentProfile studentProfile;

    @Column(name = "created_date", nullable = false)
    @CreationTimestamp
    private Calendar createdDate = Calendar.getInstance();
}
