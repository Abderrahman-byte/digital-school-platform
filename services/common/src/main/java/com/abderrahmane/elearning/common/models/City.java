package com.abderrahmane.elearning.common.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "city")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class City {
    @Id
    private int id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(targetEntity = State.class, optional = false)
    private State state;
}
