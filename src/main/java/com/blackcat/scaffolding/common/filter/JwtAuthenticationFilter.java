package com.blackcat.scaffolding.common.filter;
import com.blackcat.scaffolding.model.LoginBody;
import com.blackcat.scaffolding.model.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.blackcat.scaffolding.common.security.JwtUtil;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
/**
 * JWT认证过滤器
 * @author : zhangdahui  2025/2/26 上午10:01
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginBody body = new ObjectMapper().readValue(req.getInputStream(), LoginBody.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            body.getLoginName(),
                            body.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) {
        String token = JwtUtil.generateToken((LoginUser) auth.getPrincipal());
        res.addHeader("Authorization", "Bearer " + token);
    }
}
