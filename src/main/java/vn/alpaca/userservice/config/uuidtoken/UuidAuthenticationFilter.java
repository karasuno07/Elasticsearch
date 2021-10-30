package vn.alpaca.userservice.config.uuidtoken;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.alpaca.userservice.entity.jpa.User;
import vn.alpaca.userservice.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class UuidAuthenticationFilter extends OncePerRequestFilter {


    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");
            if (StringUtils.hasText(token)) {
                User user = tokenService.retrieveUserFromToken(token);
                if (!ObjectUtils.isEmpty(user)) {

                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(user, null,
                                                                      user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}


