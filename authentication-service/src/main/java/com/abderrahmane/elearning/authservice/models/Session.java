package com.abderrahmane.elearning.authservice.models;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Table(name = "session")
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonType.class)
})
public class Session {
    @Id
    @GenericGenerator(name = "random_string_generator", strategy = "com.abderrahmane.elearning.authservice.utils.RandomStringGenerator")
    @GeneratedValue(generator = "random_string_generator")
    private String sid;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String, Object> payload = new HashMap<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar expires = Calendar.getInstance();

    public Session () {}

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Calendar getExpires() {
        return expires;
    }

    public void setExpires(Calendar expires) {
        this.expires = expires;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public long getMaxAge () {
        if (this.expires == null) return 0;

        return (this.expires.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000;
    }
}
