package vehiclepartspro.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import vehiclepartspro.support.authentication.JwtAuthConverter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    public static final String CUSTOMER = "Customer";
    public static final String MANUFACTURER = "Manufacturer";

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {

        http.
                authorizeHttpRequests(auth ->
                {
                    auth.requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole(MANUFACTURER,CUSTOMER);
                    auth.requestMatchers(HttpMethod.POST,"/product/addProduct").hasRole(MANUFACTURER);
                    auth.requestMatchers(HttpMethod.POST, "/purchases/buyProducts").hasRole(CUSTOMER);
                    auth.requestMatchers(HttpMethod.GET,"/product/getAllProducts").permitAll();
                    auth.anyRequest().authenticated();
                });

        http.
                oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
                ));

        http.
                sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}
