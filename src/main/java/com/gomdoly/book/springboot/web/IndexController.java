package com.gomdoly.book.springboot.web;

import com.gomdoly.book.springboot.service.posts.PostsService;
import com.gomdoly.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){ // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
        // postsService.findAllDesc() 로 가져온 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts", postsService.findAllDesc());

        return "index";
        // mustache 스타터가 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 확장자를 자동 설정
        // return이 index이므로 ViewResolver는 src/main/resource/templates/index.mustache로 받게 된다.
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable("id") Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);

        model.addAttribute("post", dto);

        return "posts-update";
    }

}
