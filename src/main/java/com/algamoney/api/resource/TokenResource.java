package com.algamoney.api.resource;

import com.algamoney.api.config.property.AlgamoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenResource {

    @Autowired
    private AlgamoneyApiProperty algamoneyApiProperty;

    @DeleteMapping("/revoke")
    public void revoke(HttpServletRequest req, HttpServletResponse res) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(algamoneyApiProperty.getSeguranca().isEnableHttps());
        cookie.setPath("google.com");
        cookie.setMaxAge(0);

        res.addCookie(cookie);
        res.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
