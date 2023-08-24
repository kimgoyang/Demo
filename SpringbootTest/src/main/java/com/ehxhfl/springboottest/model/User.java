package com.ehxhfl.springboottest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Data
//@Data = @Gette + @SSetter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
@NoArgsConstructor
//파라미터가 없는 기본 생성자 생성
@AllArgsConstructor
//모든 필드 값을 파라미터로 받는 생성자 생성
//@RequiredArgsConstructor = final이나 @Nonull인 필드 값만 파라미터로 받는 생성자 생성
@Builder //빌더패턴... 나중에 다시 찾아보자
//@DynamicInsert //insert할 때 null인 필드를 제외시켜줌
//알아서 쓰면 좋지만 굳이 어노테이션 덕지덕지는 좋지 않음 그러므로 삭제
@Entity //User 클래스가 Mysql에 테이블이 생성됨.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 프로젝트에 연결된 DB의 넘버링 전략을 따라감.
    private int id;// long으로 해도 됨, 프라이머리키?

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100) // 비밀번호 암호화때문에 넉넉하게 줌
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //@ColumnDefault("'user'")
    //private String role;
    @Enumerated(EnumType.STRING) //DB에는 Role타입이 없으니까 쟤는 스트링이다 하고 알려줌
    private RoleType role;
    //Enum을 쓰는게 좋다. // admin, user, manager 등의 권한 관리가 가능
    //enum을 쓰면 domain을 정해줄 수 있다

    @CreationTimestamp //시간이 자동으로 입력됨
    private Timestamp createDate; //java.sql
}
