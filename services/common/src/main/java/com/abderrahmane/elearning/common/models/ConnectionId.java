package com.abderrahmane.elearning.common.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ConnectionId implements Serializable {
    protected String teacherId;
    protected String studentId;

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
