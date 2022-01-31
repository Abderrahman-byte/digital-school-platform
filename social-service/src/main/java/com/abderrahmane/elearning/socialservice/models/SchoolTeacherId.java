package com.abderrahmane.elearning.socialservice.models;

import java.io.Serializable;

public class SchoolTeacherId implements Serializable {
    protected String schoolId;
    protected String teacherId;
    
    public SchoolTeacherId () {}

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
