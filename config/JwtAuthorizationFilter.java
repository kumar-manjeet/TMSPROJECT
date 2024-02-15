package com.tms.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tms.services.CustomUserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
//	@Autowired
//	private UriService uriService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {


		String requestTokenHeader = request.getHeader("Authorization");
		String uri= request.getRequestURI();
		String username= null;
		String jwtToken= null;
		
		if(requestTokenHeader!=null  && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = this.jwtUtil.extractUsername(jwtToken);
			} catch (Exception e) {
				e.printStackTrace();
			}
			UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//			Set<String> rolesByUri = uriService.getRolesByUri(uri);
//			Boolean authorization = false;
//			for(GrantedAuthority data : authorities) {
//				String role = data.getAuthority();
//				if(rolesByUri.contains(role)) {
//					authorization = true;
//					break;
//				}
//			}
//			if(authorization == false) {
//				throw new UsernameNotFoundException("User Not Authorized");
//			}
			if(username!= null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}}
