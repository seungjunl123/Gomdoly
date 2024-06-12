package com.gomdoly.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
// 어노테이션이 생성될 수 있는 위치 지점
// Parameter로 지정했으니 메소드의 파라미터로 선언된 객체에서 사용가능
@Retention(RetentionPolicy.RUNTIME)
// 어노테이션이 언제까지 유지될지 결정
// Runtime으로 지정하면 메모리에서 실행될 때까지 어노테이션이 유지
public @interface LoginUser {// 이 파일을 어노테이션 클래스로 지정
}
