package com.gomdoly.book.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach // 단위 테스트가 끝날때마다 수행되는 메서드 지정
    // 여기서는 테스트용 DB인 h2를 비우기 위해 사용
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void save_test(){
        //given
        String title ="테스트 게시글";
        String content ="테스트 본문";

        postsRepository.save(Posts.builder() //save : 테이블 posts에 insert/update 쿼리를 실행
                // id가 있으면 update, 없으면 insert가 수행
                .title(title)
                .content(content)
                .author("tmdwns9112@naver.com")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll(); // 테이블의 모든 값을 불러온다

        //then
        Posts posts =postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    public void baseTimeEntity_regist(){
        // given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .content("content")
                .title("title")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>> createDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate().isAfter(now));
        assertThat(posts.getModifiedDate().isAfter(now));
    }
}
