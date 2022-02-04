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

@Entity
@Table(name = "account")
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
    private boolean isAdmin;

    @Column(name="is_active", nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "acc_type", nullable = false)
    @Type(type = "com.abderrahmane.elearning.common.helpers.EnumTypePostgreSql")
    private AccountType accountType;

    @OneToOne(targetEntity = SchoolProfile.class, optional = true, mappedBy = "account", cascade = { CascadeType.ALL })
    private SchoolProfile schoolProfile;

    @OneToOne(targetEntity = TeacherProfile.class, optional = true, mappedBy = "account", cascade = { CascadeType.ALL })
    private TeacherProfile teacherProfile;

    @OneToOne(targetEntity = StudentProfile.class, optional = true, mappedBy = "account", cascade = { CascadeType.ALL })
    private StudentProfile studentProfile;

    @Column(name = "created_date", nullable = false)
    @CreationTimestamp
    private Calendar createdDate;

    public Account () {
        this.createdDate = Calendar.getInstance();
        this.accountType = AccountType.STUDENT; 
        this.isActive = false;
        this.isAdmin = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public SchoolProfile getSchoolProfile() {
        return schoolProfile;
    }

    public void setSchoolProfile(SchoolProfile schoolProfile) {
        this.schoolProfile = schoolProfile;
    }

    public TeacherProfile getTeacherProfile() {
        return teacherProfile;
    }

    public void setTeacherProfile(TeacherProfile teacherProfile) {
        this.teacherProfile = teacherProfile;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }
}