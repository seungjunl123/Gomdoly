package com.gomdoly.book.springboot.config.auth;


import com.gomdoly.book.springboot.config.auth.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) { // 컨트롤러 메서드의 특정 파라미터 지원 여부 판단
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        // @LoginUser가 붙어있으면 true
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        // SessionUser.class이면 true

        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 파라미터에 전달할 객체를 생성
        // 여기서는 세션에서 객체를 가져온다.
        return httpSession.getAttribute("user");
    }
}
