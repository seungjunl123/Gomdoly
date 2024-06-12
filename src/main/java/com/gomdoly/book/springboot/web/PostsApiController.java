package com.gomdoly.book.springboot.web;

import com.gomdoly.book.springboot.service.posts.PostsService;
import com.gomdoly.book.springboot.web.dto.PostsResponseDto;
import com.gomdoly.book.springboot.web.dto.PostsSaveRequestsDto;
import com.gomdoly.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestsDto requestsDto){
        return postsService.save(requestsDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable("id") Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable("id") Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable("id") Long id){
        postsService.delete(id);
        return id;
    }
}
