package vn.alpaca.userservice.config.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.alpaca.userservice.entity.jpa.User;
import vn.alpaca.userservice.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider tokenProvider;
    private final UserService userService;

    private static final String AUTHENTICATION_SCHEME = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = extractTokenFromHeader(request);

        if (tokenProvider.validateToken(jwt)) {
            int userId =
                    Integer.parseInt(tokenProvider.getUserIdFromToken(jwt));
            User user = userService.findById(userId);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user, null,
                            user.getAuthorities()
                    );
            authentication.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(AUTHENTICATION_SCHEME)) {
            return bearerToken.substring(AUTHENTICATION_SCHEME.length());
        }
        return null;
    }
}
