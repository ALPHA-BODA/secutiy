package com.example.security.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "jwtSecretKey";
	private static final long EXPIRATION_DATE = 3600;
	
	public String generateToken(UserDetails user) {
		Map<String, Object> claims = new HashMap<>();
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		List<String> authoritiesNames = new ArrayList<>();
		for(GrantedAuthority authority:authorities) {
			authoritiesNames.add(authority.getAuthority());
		}
		claims.put("authorities",authoritiesNames);
		claims.put("email", user.getUsername());
		claims.put("exp", new Date(System.currentTimeMillis()+EXPIRATION_DATE*1000));
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	
	}
	
	public String extractEmail(String token) {
		Claims claims = (Claims) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
		String email = (String) claims.get("email");
		return email;
	}
	
	private Date extractExpirationDate(String token) {
		Claims claims = (Claims) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
		Date expirationDate = (Date) claims.get("exp");
		return expirationDate;
	}
	
	public boolean validateToken(String token, String email) {
		return !extractExpirationDate(token).before(new Date()) && email.equals(extractEmail(token));
	}
}
