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
@Table(name = "state")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class State {
    @Id
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = Country.class, optional = false)
    private Country country;
}
