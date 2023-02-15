package com.sparta.springwork2_blog.entity;


import com.sparta.springwork2_blog.dto.BlogRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 도메인 객체 생성
@Getter // 코드를 작성하지 않아도 get, set 함수 사용 가능 (Lombok 어노테이션)  apple이라는 필드에 선언하면 자동으로 getApple()라는 메소드를 생성해준다.
@Entity //테이블임을 나타낸다.
@NoArgsConstructor  // 매개변수가 없는 생성자 코드 삭제
public class Blog extends Timestamped{
    @Id // Id값, PK로 사용하겠다는 뜻.
    @GeneratedValue(strategy = GenerationType.AUTO) //등록할때마다 자동생성(1씩 올라가는 키), 고유 아이디
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)   // 안쓰면 빈값 들어감, 반드시 값이 존재해야 함을 뜻함
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne  // MTO의 기본 로딩 정책 : Eager Loading(자식 Entity를 조회할 때 자동으로 부모 Entity 조회해 옴) 부모 Entity를 사용할 필요가 없다면 속도를 위해 (fetch = FetchType.LAZY)로 설정할 수 있다.즉시 데이터를 DB에서 가지고 오는 것.
    @JoinColumn(name = "user_id", nullable = false) //name속성에는 매핑할 외래 키 이름 지정(생략 가능) 현재 엔티티(테이블) 기준으로 조인의 대상으로 사용할 컬럼의 이름 지정 어노테이션
    private User user;

    @Builder
    public Blog(BlogRequestDto blogrequestDto, User user){ // 생성자 오버로딩
        this.id = blogrequestDto.getId();
        this.username = user.getUsername();
        this.title = blogrequestDto.getTitle();
        this.contents = blogrequestDto.getContents();
        this.user = user;
    }

    public void update(BlogRequestDto blogRequestDto,User user) {
        this.title = blogRequestDto.getTitle();
        this.contents = blogRequestDto.getContents();
        this.user = user;
    }

}
