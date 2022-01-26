package com.abderrahmane.elearning.socialservice.models;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "session")
public class Session {
    @Id
    private String sid;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String, Object> payload = new HashMap<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar expires;

    public Session() {
    }

    public Session(String sid, Map<String, Object> payload) {
        this(payload);
        this.sid = sid;
    }

    public Session(Map<String, Object> payload) {
        this.payload = payload;
        this.expires = Calendar.getInstance();
    }

    public Session(String sid, Map<String, Object> payload, Calendar expires) {
        this(payload, expires);
        this.sid = sid;
    }

    public Session(Map<String, Object> payload, Calendar expires) {
        this.payload = payload;
        this.expires = expires;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public Calendar getExpires() {
        return expires;
    }
}
