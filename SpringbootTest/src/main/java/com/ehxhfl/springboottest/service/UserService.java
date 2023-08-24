package com.ehxhfl.springboottest.service;

import com.ehxhfl.springboottest.model.User;
import com.ehxhfl.springboottest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌 = IoC 해준다. = 메모리에 띄워준다.
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional //SpringFramework
    //전체가 성공하면 commit, 실패하면 rollback
    public int 회원가입(User user) {
        try {
            userRepository.save(user);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserService : 회원가입() : " + e.getMessage());
            return -1;
        }
    }

    @Transactional(readOnly = true)
    //Select할 때 트랜잭션이 시작, 서비스 종료 시에 트랜잭션 종료 = 정합성 유지
    public User 로그인 (User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
