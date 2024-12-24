package com.ll.jpatest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JpaTestApplicationTests {

    @Autowired
    private TestOneRepository testOneRepository;

    @Autowired
    private TestManyRepository testManyRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("generate TestEntityOne")
    public void t1() {
        TestEntityOne testEntityOne = new TestEntityOne();
        testOneRepository.save(testEntityOne);
    }

    @Test
    @DisplayName("generate UserEntity")
    public void t2() {
        for (int i =0; i<10;i++) {
            UserEntity userEntity = new UserEntity();
            userEntityRepository.save(userEntity);
        }
    }

    @Test
    @DisplayName("generate TestEntityMany")
    @Transactional
    @Rollback(false)
    public void t3() {
        TestEntityOne testEntityOne1 = testOneRepository.findById(1).get();
        UserEntity userEntity1 = userEntityRepository.findById(1).get();
        UserEntity userEntity2 = userEntityRepository.findById(2).get();
        UserEntity userEntity3 = userEntityRepository.findById(3).get();

        TestEntityMany testEntityMany1 = new TestEntityMany();
        testEntityMany1.getVoter().add(userEntity1);
        testEntityMany1.getVoter().add(userEntity2);
        testEntityMany1.getVoter().add(userEntity3);
        testEntityMany1.setTestEntityOne(testEntityOne1);
        testManyRepository.save(testEntityMany1);

        TestEntityMany testEntityMany2 = new TestEntityMany();
        testEntityMany2.getVoter().add(userEntity1);
        testEntityMany2.getVoter().add(userEntity2);
        testEntityMany2.setTestEntityOne(testEntityOne1);
        testManyRepository.save(testEntityMany2);

        for (int i = 1; i <= 100; i++) {
            TestEntityMany testEntityMany = new TestEntityMany();
            testEntityMany.setTestEntityOne(testEntityOne1);
            testManyRepository.save(testEntityMany);
        }

    }

    @Test
    @DisplayName("find by paging")
    public void t4() {
        TestEntityOne testEntityOne = testOneRepository.findById(1).get();
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("voter"));
        sorts.add(Sort.Order.asc("id"));

        //when pageNumber is 1, it works well,
        //but if pageNumber is 0 it fails.
        Pageable pageable = PageRequest.of(0,5, Sort.by(sorts));
        Page<TestEntityMany> testEntityManyPage = testManyRepository.findAllByTestEntityOne(testEntityOne, pageable);
        assertThat(testEntityManyPage.getTotalElements()).isEqualTo(102);
    }
}
