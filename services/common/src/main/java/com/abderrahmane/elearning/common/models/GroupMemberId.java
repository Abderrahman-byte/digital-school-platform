package com.abderrahmane.elearning.common.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class GroupMemberId implements Serializable {
    protected String groupId;
    protected String memberId;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!obj.getClass().equals(GroupMemberId.class)) return false;

        GroupMemberId target = (GroupMemberId)obj;

        return target.groupId.equals(this.groupId) && target.memberId.equals(this.memberId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        
        int result = 1;
        result = prime * result + (this.groupId != null ? this.groupId.hashCode() : null); 
        result = prime * result + (this.memberId != null ? this.memberId.hashCode() : null); 

        return result;
    }
}
