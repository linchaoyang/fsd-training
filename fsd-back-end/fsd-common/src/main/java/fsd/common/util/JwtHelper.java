package fsd.common.util;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.userdetails.UserDetails;

import fsd.common.config.JwtConfig;
import fsd.common.model.auth.FsdUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtHelper {

	private JwtConfig config;

	/** base64 security key */
	private byte[] apiKeySecretBytes;

	public JwtHelper(JwtConfig config) {
		this.config = config;
		apiKeySecretBytes = DatatypeConverter.parseBase64Binary(config.getSalt());
	}

	/**
	 * decode jwt.
	 * 
	 * @param jsonWebToken token transfer from client
	 * @return
	 */
	public Claims getClaimsFromToken(String jsonWebToken) {
		try {
			Claims claims = Jwts.parser().setSigningKey(apiKeySecretBytes).parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			return null;
		}
	}

	// /**
	// * Generate token.
	// *
	// * @param user UserDetails
	// * @return token
	// */
	// public static String generateToken(UserDetails user) {
	// SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	// // create siging Secret key
	// Key signingKey = new SecretKeySpec(apiKeySecretBytes,
	// signatureAlgorithm.getJcaName());

	// // create JWT builder
	// JwtBuilder builder = Jwts.builder()
	// // set header "typ" as JWT
	// .setHeaderParam("typ", "JWT")
	// // add role
	// .claim("role", user.getAuthorities())
	// // add username
	// .claim("username", user.getUsername())
	// // set issuer
	// .setIssuer(ISSUER)
	// // set subject to who
	// .setSubject(user.getUsername())
	// // issue date
	// .setIssuedAt(new Date())
	// // sign
	// .signWith(signatureAlgorithm, signingKey)
	// // validate from
	// .setNotBefore(new Date())
	// // expire to
	// .setExpiration(new Date(System.currentTimeMillis() + expire));

	// // Create JWT
	// return builder.compact();
	// }

	public String generateToken(UserDetails user) {
		Map<String, Object> claims = new HashMap<>();
		// set subject to who
		claims.put("sub", user.getUsername());
		// add user id
		claims.put("userId", ((FsdUserDetail) user).getUserId());
		// add user type
		claims.put("userType", ((FsdUserDetail) user).getUserType());
		// add authorities
		claims.put("authorities",
				user.getAuthorities().stream().map(role -> role.getAuthority()).toArray(String[]::new));
		claims.put("created", new Date());
		return generateToken(claims);
	}

	/**
	 * Generate token from claims
	 *
	 * @param claims 数据声明
	 * @return 令牌
	 */
	public String generateToken(Map<String, Object> claims) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		// create siging Secret key
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// validate to
		LocalDateTime issueDate = LocalDateTime.now();
		Instant instant = issueDate.plusDays(this.config.getExpire()).toInstant(ZoneOffset.UTC);

		Date startDate = Date.from(issueDate.toInstant(ZoneOffset.UTC));
		Date expirationDate = Date.from(instant);
		return Jwts.builder()
				// set header "typ" as JWT
				.setHeaderParam("typ", "JWT")
				// set claims
				.setClaims(claims)
				// set issuer
				.setIssuer(this.config.getIssuer())
				// set subject to who
				// .setSubject(user.getUsername())
				// issue date
				.setIssuedAt(startDate)
				// sign
				.signWith(signatureAlgorithm, signingKey)
				// validate from
				.setNotBefore(startDate)
				// expire to
				.setExpiration(expirationDate).compact();
	}

	/**
	 * 从令牌中获取用户名
	 *
	 * @param token 令牌
	 * @return 用户名
	 */
	public String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * 从令牌中获取用户类型
	 *
	 * @param token 令牌
	 * @return 用户类型
	 */
	public String getUserTypeFromToken(String token) {
		String userType;
		try {
			Claims claims = getClaimsFromToken(token);
			userType = claims.get("userType").toString();
		} catch (Exception e) {
			userType = null;
		}
		return userType;
	}

	/**
	 * Get authorities from token
	 *
	 * @param token 令牌
	 * @return authorities
	 */
	public String[] getAuthoritiesFromToken(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			return (String[]) claims.get("authorities");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 从令牌中获取用户Id
	 *
	 * @param token 令牌
	 * @return 用户Id
	 */
	public String getUserIdFromToken(String token) {
		String userId;
		try {
			Claims claims = getClaimsFromToken(token);
			userId = claims.get("userId").toString();
		} catch (Exception e) {
			userId = null;
		}
		return userId;
	}

	/**
	 * 判断令牌是否过期
	 *
	 * @param token 令牌
	 * @return 是否过期
	 */
	public Boolean isTokenExpired(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			Date expiration = claims.getExpiration();
			return expiration.before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 刷新令牌
	 *
	 * @param token 原令牌
	 * @return 新令牌
	 */
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put("created", new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * 验证令牌
	 *
	 * @param token 令牌
	 * @param user  用户
	 * @return 是否有效
	 */
	public Boolean validateToken(String token, UserDetails user) {
		String username = user.getUsername();
		String usernameFromToken = getUsernameFromToken(token);
		return (usernameFromToken.equals(username) && !isTokenExpired(token));
	}
}