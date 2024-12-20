package com.bill.tech.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.bill.tech.authentication.EntryPoint;
import com.bill.tech.authentication.JwtFiltetr;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfigurations {
	public static final String[] URL_CONST_FOR_ADMIN_AND_CUSTOMER = { "/api/**" };

	public static final String[] OPEN_REQUEST = { "/auth/**" };
	@Autowired
	private EntryPoint point;
	@Autowired
	private JwtFiltetr filter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(getCorsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth.requestMatchers(OPEN_REQUEST).permitAll()
						.requestMatchers(URL_CONST_FOR_ADMIN_AND_CUSTOMER)
						// .hasAnyRole("CUSTOMER", "ADMIN")
						// .requestMatchers("/api/**")
						.authenticated().anyRequest().authenticated())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public CorsConfigurationSource getCorsConfigurationSource() {
		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:3000"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setExposedHeaders(Arrays.asList("Authorization"));
				config.setMaxAge(3600L);
				return config;

			}
		};
	}
}
