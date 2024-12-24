package com.ll.jpatest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Entity
public class TestEntityOne {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(mappedBy = "testEntityOne")
    private List<TestEntityMany> testEntityManyList;
}
