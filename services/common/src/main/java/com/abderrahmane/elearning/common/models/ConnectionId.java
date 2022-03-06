package com.abderrahmane.elearning.common.models;

import java.io.Serializable;

public class ConnectionId implements Serializable {
    protected String teacherId;
    protected String studentId;

    public ConnectionId () {}

    public String getStudentId() {
        return studentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj == this) return true;

        if (obj.getClass() != this.getClass()) return false;

        ConnectionId target = (ConnectionId)obj;
        
        return target.studentId.equals(this.studentId) && target.teacherId.equals(this.teacherId);
    }

     // FIXME : Idk how this method works
     @Override
     public int hashCode() {
         final int prime = 31;
         int result = 1;
         result = prime * result + ((this.studentId == null) ? 0 : this.studentId.hashCode());
         result = prime * result + ((this.teacherId == null) ? 0 : this.teacherId.hashCode());
         return result;
     }
}
