package com.gomdoly.book.springboot.web;

import com.gomdoly.book.springboot.config.auth.LoginUser;
import com.gomdoly.book.springboot.config.auth.dto.SessionUser;
import com.gomdoly.book.springboot.domain.user.User;
import com.gomdoly.book.springboot.service.posts.PostsService;
import com.gomdoly.book.springboot.web.dto.PostsResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){ // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
        // 기존의 httpSession에서 가져왔던 정보 값을 @LoginUser 어노테이션으로 대체

        // postsService.findAllDesc() 로 가져온 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts", postsService.findAllDesc());

        if(user!=null){ // 세션에 저장된 값이 있으면 model에 userName이 등록, 없으면 그대로 로그인 버튼이 보임
            model.addAttribute("userName",user.getName());
        }

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
