package com.ehxhfl.springboottest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity //얘가 클래스 가까이 있는게 좋음
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터에 쓰는 어노테이션
    private String content; //섬머노트 라이브러리 <html> 태그가 섞여서 디자인 된다. 뭔 말인지 모르겠다.

    @ColumnDefault("0")
    private int count; //조회수

    @ManyToOne(fetch = FetchType.EAGER)
    //Many = Board, One = User => 한명의 User는 여러 개의 Board를 작성할 수 있다.
    //(fetch = FetchType.EAGER) 하나 밖에 없으니까 바로 JOIN해서 갖고 오껭
    @JoinColumn(name = "userId") //DB 저장될때 필드값이 userId가 됨.
    private User user;
    //private int userId;
    //DB는 오브젝트를 저장할 수 없음. FK, 자바는 오브젝트를 저장할 수 있음
    //자바가 알아서 맞춰서 해줘야함. 대신 어노테이션 넣어서 해결함

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    //(fetch = FetchType.LAZY) 필요하면 들고 오고 아님 말고
    //원래 OneToMany의 기본 전략이 LAZY인데 여기선 일단 Reply 저옵를 들고 와야됨 따라서 EAGER를 쓰겠다 ㅇㅇ
    //mappedBy = 연관관계의 주인이 내가 아님(FK 없음) = DB에 칼럼을 만들지 마셈
    //(mappedBy = "필드 이름")
    //@JoinColumn(name = "replyId")가 필요 없음
    private List<Reply> reply;

    @CreationTimestamp //데이터가 insert 혹은 update 될때 시간이 자동으로 올라감
    private Timestamp createDate; //java.sql
}
