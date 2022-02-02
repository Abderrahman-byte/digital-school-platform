package com.abderrahmane.elearning.common.models;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj == this) return true;

        if (obj.getClass() != this.getClass()) return false;


        SchoolTeacherId target = (SchoolTeacherId)obj;
        
        return target.schoolId.equals(this.schoolId) && target.teacherId.equals(this.teacherId);
    }

    // FIXME : Idk how this method works
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.schoolId == null) ? 0 : this.schoolId.hashCode());
        result = prime * result + ((this.teacherId == null) ? 0 : this.teacherId.hashCode());
        return result;
    }
}
