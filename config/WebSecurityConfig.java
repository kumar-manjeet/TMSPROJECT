package com.tms.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;

import com.tms.services.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableCaching
public class WebSecurityConfig {

	@Autowired
	private JwtAuthorizationFilter jwtAuthorizationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//		http
//			.csrf().disable()
////			.cors().disable()
//			.authorizeHttpRequests()
////			.requestMatchers("/rest/home/**").hasRole("USER")
////			.requestMatchers("/login/**").permitAll()
//			.requestMatchers("/auth/**").permitAll()
////			.requestMatchers("/test/**").permitAll()
////			.antMatchers("/msgSend/sendOtpByMail").permitAll()
////			.antMatchers("/**").hasRole("ADMIN")
//			.anyRequest()
//			.authenticated()
//			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
//			.formLogin()
//			.loginPage("/auth/signin")
//			.loginProcessingUrl("/dologin")
//			.defaultSuccessUrl("/test/test");
////			.failureUrl("/test/test");
////			.failureHandler(authenticationFailureHandler());

		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((requests) -> {
			requests.requestMatchers("/signin").permitAll();
			requests.requestMatchers("/login/**").permitAll();

//			requests.anyRequest().authenticated();
			requests.anyRequest().permitAll();
		}).formLogin((form) -> form.loginPage("/signin").loginProcessingUrl("/dologin").defaultSuccessUrl("/home")
				.permitAll()).logout((logout) -> logout.permitAll());

		http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000/", "https://lonch.vercel.app/"));
		corsConfiguration
				.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
//	        corsConfiguration.setAllowCredentials(true);
//	        corsConfiguration.setExposedHeaders(List.of("Authorization"));
		http.cors().configurationSource(request -> corsConfiguration);

		return http.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailService() {
		return new CustomUserDetailService();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("apiResponseCache");
	}
}
