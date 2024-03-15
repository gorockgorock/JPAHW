package com.sparta.travelnewsfeed.aop;

import com.sparta.travelnewsfeed.entity.ApiUseTime;
import com.sparta.travelnewsfeed.repository.ApiUseTimeRepository;
import com.sparta.travelnewsfeed.user.User;
import com.sparta.travelnewsfeed.user.UserDetailsImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UseTimeAop {

  private final ApiUseTimeRepository apiUseTimeRepository;

  public UseTimeAop(ApiUseTimeRepository apiUseTimeRepository) {
    this.apiUseTimeRepository = apiUseTimeRepository;
  } //@RequiredArgsConstructor로 대체 가능

  @Pointcut("execution(* com.sparta.travelnewsfeed.controller.PostController.*(..))")
  private void post() {}
  @Pointcut("execution(* com.sparta.travelnewsfeed.controller.UserController.*(..))")
  private void comment() {}

  @Around("post() || comment()")
  public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
    // 측정 시작 시간
    long startTime = System.currentTimeMillis();

    try {
      // 핵심기능 수행
      Object output = joinPoint.proceed();
      return output;
    } finally {
      // 측정 종료 시간
      long endTime = System.currentTimeMillis();
      // 수행시간 = 종료 시간 - 시작 시간
      long runTime = endTime - startTime;

      // 로그인 회원이 없는 경우, 수행시간 기록하지 않음
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
        // 로그인 회원 정보
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User loginUser = userDetails.getUser();

        // API 사용시간 및 DB 에 기록
        ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser).orElse(null);
        if (apiUseTime == null) {
          // 로그인 회원의 기록이 없으면
          apiUseTime = new ApiUseTime(loginUser, runTime);
        } else {
          // 로그인 회원의 기록이 이미 있으면
          apiUseTime.addUseTime(runTime);
        }

        apiUseTimeRepository.save(apiUseTime);
      }
    }
  }
}
