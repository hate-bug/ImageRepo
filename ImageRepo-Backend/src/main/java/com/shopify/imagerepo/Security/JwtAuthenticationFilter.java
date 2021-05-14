package com.shopify.imagerepo.Security;

import com.shopify.imagerepo.Model.User;
import com.shopify.imagerepo.Service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = httpServletRequest.getHeader(SecurityURLs.HEADER_STRING);
            if (StringUtils.hasText(token) && token.startsWith(SecurityURLs.Token_Prefix)) {
                token = token.substring(7, token.length()); //Get JWT token
                if (jwtProvider.validateJwt(token)) {
                    Long userId = jwtProvider.getUserIdfromJwt(token);
                    User user = myUserDetailService.loadUserById(userId);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, Collections.emptyList());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        } catch (Exception e) {
            logger.error("Cannot authenticate user with provided JWT toke.", e);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
