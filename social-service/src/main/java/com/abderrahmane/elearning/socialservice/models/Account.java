package com.abderrahmane.elearning.socialservice.models;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "account")
public class Account {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "acc_type", nullable = false)
    @Type( type = "com.abderrahmane.elearning.socialservice.helpers.PostgresqlEnumType")
    private AccountType accountType;

    @OneToOne(targetEntity = SchoolProfile.class, mappedBy = "account", optional = true, cascade = { CascadeType.ALL })
    private SchoolProfile schoolProfil;

    @OneToOne(targetEntity = TeacherProfile.class, mappedBy = "account", optional = true, cascade = { CascadeType.ALL })
    private TeacherProfile teacherProfil;

    @OneToOne(targetEntity = StudentProfile.class, mappedBy = "account", optional = true, cascade = { CascadeType.ALL })
    private StudentProfile studentProfile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Calendar createdDate;

    public Account () {}

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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public SchoolProfile getSchoolProfil() {
        return schoolProfil;
    }

    public void setSchoolProfil(SchoolProfile schoolProfil) {
        this.schoolProfil = schoolProfil;
    }

    public TeacherProfile getTeacherProfil() {
        return teacherProfil;
    }

    public void setTeacherProfil(TeacherProfile teacherProfil) {
        this.teacherProfil = teacherProfil;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }
}
