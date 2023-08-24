package com.ehxhfl.springboottest.test;

import com.ehxhfl.springboottest.model.RoleType;
import com.ehxhfl.springboottest.model.User;
import com.ehxhfl.springboottest.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RestController = html파일이 아니라 data를 리턴해주는 controller
public class DummyControllerTest {

    @Autowired //의존성 주입 (DI)
    //DummyControllerTest가 메모리에 뜨면 UserRepository도 메모리에 같이 띄워라.
    //@Autowired = UserRepository 타입으로 스프링이 관리하고 있는게 있다면 userRepository에 쏙 넣어줘라
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        /*try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e   ) {
            return "삭제에 실패하였습니다. 해당 id는 DB에 존재하지 않습니다.";
        }

        return "삭제되 었습니다. id : " + id;*/

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return "삭제 성공 : " + id;
        } else {
            return "삭제 실패 : " + id;
        }
    }

    @Transactional //더티체킹?? save를 안해도 업데이트가 된다.
    @PutMapping("/dummy/user/{id}")
    //@RequestBody : JSON을 받으려고 할때 필요한 어노테이션
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
        //Json 데이터를 요청 -> JAVA Object로 변환이 알아서 됨.(MessageConverter의 Jackson Library가 해줌. 개꿀 ^^b)
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()-> { //영속화
            return new IllegalArgumentException("정보 수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        /*userRepository.save(user);*/
        //그냥 save를 하게 되면 입력받지 않은 username이나 role등이 null값으로 변함.
        //따라서 user의 원래 정보를 받고 따로 입력 받은 password랑 email을 바꿔놓고 값을 반환해야함.
        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    //한 페이지 당 2 건의 데이터를 리턴 받는 함수
    //http://localhost:8000/blog/dummy/user -> 가장 최근에 DB에 저장된 데이터 2 건을 보여줌
    //http://localhost:8000/blog/dummy/user?page=1 -> 1 페이지의 2 건을 보여줌 -> page는 0부터 시작
    /*@GetMapping("/dummy/user")
    public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }*/

    //페이지의 자잘한 내용 없이 content 안의 내용만 필요한 경우의 함수
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 1, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }

    //{id}주소로 파라메터를 전달 받을 수 있음.
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        /*User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 존재하지 않습니다. ID : " + id);
            }
        });*/

        //람다식

        User user = userRepository.findById(id).orElseThrow(()->{
           return new IllegalArgumentException("해당 사용자는 존재하지 않습니다. ID : " + id);
        });

        //요청 : WebBrowser
        //근데 user객체 = JAVA Object 따라서 웹이 이해못함.
        //그래서 변환을 해야함 = JSON으로 변환해주자 ㅇㅇ
        //SpringBoot에선 MessageConverter가 응답시에 알아서 작동을 해서 Jackson라이브러리를 호출해서 user Object를 Json으로 변환해서 브라우저한테 줌
        return user;
    }

    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("id : " + user.getId());
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());
        System.out.println("role : " + user.getRole());
        System.out.println("createDate : " + user.getCreateDate());

        //user.setRole("user");
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입 완료 굳 ^^b";
    }
}
