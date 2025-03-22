package com.hook4startup.Filter;

import com.hook4startup.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.List;

@Component
public class CustomTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ **Bypass token validation for `/auth/**` APIs**
        String path = request.getRequestURI();
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;

      // ✅ **Extract token from cookies**
       if (request.getCookies() != null) {
          for (var cookie : request.getCookies()) {
               if ("session_token".equals(cookie.getName())) { // ✅ Ensure correct cookie name
                    token = cookie.getValue();
                    break;
                }
           }
       }
            // token="12f80d22-bc03-4816-9d7c-fe37cda03f92";
        System.out.println("🔍 Extracted Token: " + token); // Debugging

        if (token != null) {
            Optional<String> username = tokenService.validateToken(token);
            if (username.isPresent()) {
                System.out.println("✅ Token Valid. User: " + username.get());

                // ✅ **Set Authentication**
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username.get(), null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
                return;
            } else {
                System.out.println("❌ Invalid Token");
            }
        } else {
            System.out.println("❌ No Token Found");
        }

        // ❌ **Unauthorized response (only for secured routes)**
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
