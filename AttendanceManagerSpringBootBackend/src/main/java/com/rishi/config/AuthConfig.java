package com.rishi.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class AuthConfig {

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider daoProvider=new DaoAuthenticationProvider();
		daoProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		daoProvider.setUserDetailsService(userDetailsService);
		return daoProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		
		return http
				.csrf(csrf->csrf.disable())
				.authorizeHttpRequests(authRequests->authRequests.requestMatchers("/register/*")
						.permitAll()
						.requestMatchers("/teacher/**")
						.hasRole("TEACHER")
						.anyRequest().authenticated())
				.cors(corsConfig->corsConfig.configurationSource(corsConfig()))
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(
						session->session
						.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.sessionFixation()//FOR SESSION FIXATION
						.changeSessionId()//WILL CHANGE SESSION ID AFTER LOGIN
						.maximumSessions(1)
						.maxSessionsPreventsLogin(true))
				.build();
	}
	@Bean
	public CorsConfigurationSource corsConfig() {
		CorsConfiguration config=new CorsConfiguration();
		config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
		config.setAllowedMethods(Arrays.asList("GET","PUT","POST","DELETE","OPTIONS"));
		config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	public CookieSerializer cookieSerializer() {
		 DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		    serializer.setCookiePath("/"); // Set the correct path
		    serializer.setCookieName("JSESSIONID");
		    serializer.setUseHttpOnlyCookie(true);
		    serializer.setSameSite("Lax"); // Set SameSite attribute for cross-origin requests
		    return serializer;

	}
}