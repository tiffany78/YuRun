package com.example.YuRun;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;

@Aspect
@Component
public class AuthorizationAspect {
    private HttpSession session;


    public AuthorizationAspect(HttpSession session){
        this.session = session;
    }

    @Before("@annotation(requiredRole)")
    public void checkAuthorization(JoinPoint joinPoint, RequiredRole requiredRole) throws Throwable{
        // Mendapatkan HttpServletResponse dari konteks request
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if (response == null) {
            throw new IllegalStateException("Response object is null");
        }

        String email = (String) session.getAttribute("email");
        if (email == null || email.isEmpty()) {
            // Redirect ke halaman login jika email tidak ditemukan
            response.sendRedirect("/error404");
            return;
        }

        String peran = (String) session.getAttribute("peran");
        String[] roles = requiredRole.value();

        if (!Arrays.asList(roles).contains(peran)) {
            // throw new SecurityException("Wrong role!");
            response.sendRedirect("/errorWrong");
            return;
        }
    }    
}
