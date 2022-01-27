package com.abderrahmane.elearning.socialservice.models;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "school_profil")
public class SchoolProfil {
    @Id
    @Column(name = "account_id")
    private String id;

    @OneToOne(targetEntity = Account.class, optional = false)
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = City.class, optional = false, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "location")
    private City location;

    @Column
    private String subtitle;

    @Column
    private String overview;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Calendar createdDate = Calendar.getInstance();

    public SchoolProfil () {}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getLocation() {
        return location;
    }

    public void setLocation(City location) {
        this.location = location;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }
}
