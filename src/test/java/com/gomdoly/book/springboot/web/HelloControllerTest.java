package com.gomdoly.book.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// JUnit 5부터는 ExtendWith 사용
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello_return() throws Exception{
        String hello ="hello";
        // api를 요청할 때 MockMvcRequestBuilders를 활용해야 한다.
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));

    }

    @Test
    public void helloDto_return() throws Exception{
        String name = "hello";
        int amount = 1000;
        mvc.perform(get("/hello/dto")
                // 요청 파라미터 설정(String값만 가능!!)
                .param("name",name)
                .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                //json 응답값을 필드별로 검증
                // $를 기준으로 필드명 명시(name,amount)
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));
    }
}