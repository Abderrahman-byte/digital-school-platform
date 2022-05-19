package com.abderrahmane.elearning.common.models;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group_member")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@IdClass(GroupMemberId.class)
public class GroupMember {
    @Id
    @Column(name = "group_id")
    private String groupId;

    @Id
    @Column(name = "member_id")
    private String memberId;

    @ManyToOne(targetEntity = Group.class, optional = false)
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(targetEntity = StudentProfile.class, optional = false)
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private StudentProfile student;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    @CreationTimestamp
    private Calendar createdDate = Calendar.getInstance();
}
