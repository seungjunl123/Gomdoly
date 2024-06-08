package com.gomdoly.book.springboot.service.posts;


import com.gomdoly.book.springboot.domain.posts.Posts;
import com.gomdoly.book.springboot.domain.posts.PostsRepository;
import com.gomdoly.book.springboot.web.dto.PostsListsResponseDto;
import com.gomdoly.book.springboot.web.dto.PostsResponseDto;
import com.gomdoly.book.springboot.web.dto.PostsSaveRequestsDto;
import com.gomdoly.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    // Autowired없이도 RequiredArgsConstructor를 통해서 롬복이 bean을 주입받게 해준다
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestsDto requestsDto){

        return postsRepository.save(requestsDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 사용자가 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) // readOnly=true 설정 시 조회 기능만 유지되어 조회속도가 개선된다
    public List<PostsListsResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListsResponseDto::new) // .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
        // postsRepository의 결과로 넘어온 Posts의 stream을 map을 통해
        // PostsListResponseDto로 변환하고 List로 반환하는 메소드
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));

        postsRepository.delete(posts);
    }
}
