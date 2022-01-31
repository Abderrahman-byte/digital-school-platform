package com.abderrahmane.elearning.socialservice.models;

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

@Entity
@Table(name = "teacher_profil")
public class TeacherProfile {
    @Id
    @Column(name = "account_id")
    private String id;

    @OneToOne(targetEntity = Account.class, optional = false)
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column
    private String title;

    @Column
    private String bio;

    @ManyToOne(targetEntity = City.class, optional = false)
    @JoinColumn(name = "location")
    private City location;

    @OneToMany(targetEntity = SchoolTeacher.class, mappedBy = "teacher", orphanRemoval = true)
    private List<SchoolProfile> schooles = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Calendar createdDate = Calendar.getInstance();

    public TeacherProfile() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SchoolProfile> getSchooles() {
        return schooles;
    }

    public void setSchooles(List<SchoolProfile> schooles) {
        this.schooles = schooles;
    }
}
