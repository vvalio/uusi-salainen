package dev.valiov.web.salainen.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableMethodSecurity
public class AuthConfig {
    @Value("${auth.client-id}")
    private String clientId;

    @Autowired
    private Environment env;

    private static final String ACCESS_ROLE = "salainen-user";

    private JwtAuthenticationConverter authenticationConverter() {
        var authenticationConverter = new JwtAuthenticationConverter();
        var authoritiesConverter = realmRolesAuthoritiesConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> authoritiesConverter.convert(jwt.getClaims()));

        return authenticationConverter;
    }

    @SuppressWarnings("unchecked")
    private AuthoritiesConverter realmRolesAuthoritiesConverter() {
        return claims -> {
            var realmAccess = Optional.ofNullable((Map<String, Object>) claims.get("realm_access"));
            var realmRoles = realmAccess.flatMap(map -> Optional.ofNullable((List<String>) map.get("roles")));

            return realmRoles.stream()
                    .flatMap(Collection::stream)
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .toList();
        };
    }

    @Bean
    SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(resourceServer -> {
            resourceServer.jwt(jwtDecoder -> {
                jwtDecoder.jwtAuthenticationConverter(authenticationConverter());
            });
        });

        http.sessionManagement(sessions -> {
            sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }).csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(requests -> {
            if (env.matchesProfiles("dev")) {
                requests.requestMatchers("/swagger-ui/**").permitAll();
                requests.requestMatchers("/v3/api-docs/**").permitAll();
            }

            requests.anyRequest().hasAnyAuthority(ACCESS_ROLE);
        });

        return http.build();
    }
}

interface AuthoritiesConverter extends Converter<Map<String, Object>, Collection<GrantedAuthority>> {
}