package com.ehxhfl.springboottest.repository;

import com.ehxhfl.springboottest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//DAO
//자동으로 Bean으로 등록이 됨 = @Repository 어노테이션 생략 가능
public interface UserRepository extends JpaRepository <User, Integer> {
    //User Table이 관리하는 Repository, PK = Integer

    //JPA Naming 전략(쿼리)
    //SELECT*FROM user WHERE username = ?1(username) AND password = ?2(password); 인 쿼리문이 자동 동작함
    User findByUsernameAndPassword(String username, String password);

    //아래처럼도 쓸 수 있다 ㅇㅇ
    /*@Query(value = "SELECT*FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
    User login(String username, String password);*/
}