package com.ll.jpatest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestManyRepository extends JpaRepository<TestEntityMany, Integer> {
    Page<TestEntityMany> findAllByTestEntityOne(TestEntityOne testEntityOne, Pageable pageable);
}
