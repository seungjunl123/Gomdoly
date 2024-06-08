package com.gomdoly.book.springboot.domain.posts;

import com.gomdoly.book.springboot.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // JPA에서 DB 테이블과 연결될 클래스 지정
public class Posts extends BaseTimeEntity { // 클래스의 camelCase 이름을 under_score 이름으로 매칭

    @Id // 테이블의 PK 필드
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    // auto Increment 속성 추가
    private Long id;

    // column 표시를 위한 어노테이션
    // 어노테이션을 사용하는 경우는 특별하게 설정을 변경하고 싶을 때
    // VARCHAR 길이를 500으로 한다던가, 타입을 TEXT로 바꾸려고 한다던가
    @Column(name="title",length = 500, nullable = false)
    private String title;

    @Column(name="content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name="author")
    private String author;

    @Builder// 해당 클래스의 빌더 패턴 클래스 생성
    // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함된다!!
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;

    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
