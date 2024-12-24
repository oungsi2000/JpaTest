package com.ll.jpatest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class TestEntityMany {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany
    Set<UserEntity> voter = new HashSet<>();;

    @ManyToOne
    private TestEntityOne testEntityOne;
}
