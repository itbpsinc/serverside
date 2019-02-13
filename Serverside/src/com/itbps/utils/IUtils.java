package com.itbps.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.itbps.fuelmgt.Authval;
import com.itbps.user.security.UserSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

public class IUtils 
{
	
	private static final String REALM = "com.itbps";
	public static final String AUTHENTICATION_SCHEME = "ITBPSINC ";
	private static final String SECRET = "ITBPSINC Woodbridge VA 22193";
	
	public static String createJWT(Authval auth) throws Exception
	{
		Map<String, Object> headers;

		headers = new HashMap<String, Object>()
		{
			{
				put("alg", "RS256");
				put("typ", "JWT");
			}
		};

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		String realsecret =IUtils.SECRET;

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(realsecret);

		Claims claims = new DefaultClaims();
		claims.put("role", auth.getRole());
		claims.put("name", auth.getName());
		claims.put("loginid", auth.getLoginid());
		claims.setId(auth.getId());
		claims.setIssuer(auth.getIssue());
		claims.setIssuedAt(new Date());

		long expir = System.currentTimeMillis() + auth.getExpiration();
		

		claims.setExpiration(new Date(expir));
		
		JwtBuilder builder = Jwts.builder().setHeader(headers).setHeaderParam("ITBPS", "Authorization").setClaims(claims).signWith(SignatureAlgorithm.HS256, apiKeySecretBytes);

		if (auth.getExpiration() >= 0)
		{
			long expMillis = nowMillis + auth.getExpiration();
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		// Builds the JWT and serializes it to a compact, URL-safe string
		return  runQuickVal(builder.compact());

	}
	
	public static String  createJWT(String id, String issuer, String payload, long ttlMillis)
	{
		Map<String, Object> headers;

		headers = new HashMap<String, Object>()
		{
			{
				put("alg", "RS256");
				put("typ", "JWT");
			}
		};

		// The JWT signature algorithm we will be using to sign the token
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		String realsecret =IUtils.SECRET;;

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(realsecret);

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setIssuer(issuer).setAudience(payload)
				.signWith(SignatureAlgorithm.HS256, apiKeySecretBytes);
		// if it has been specified, let's add the expiration
		
		if (ttlMillis >= 0)
		{
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return  runQuickVal(builder.compact());

	}
	
	public static  String runQuickVal(String jwt) 
	{

		String userid = null;
		try
		{
		   String realsecret =IUtils.SECRET;
		   Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(realsecret)).parseClaimsJws(jwt)
				.getBody();
		   if (claims != null)
		   {
			   return jwt;
		   }
		}
		catch(Exception _exx)
		{
			return null;
		}
		return jwt;
	}

	public static  String isValidToken(String jwt) 
	{

		String userid = null;
		try
		{
		   String realsecret =IUtils.SECRET;
		   Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(realsecret)).parseClaimsJws(jwt)
				.getBody();
		   if (claims != null)
		   {
			   userid =  (String)claims.get("loginid");
			   return userid;
		   }
		}
		catch(Exception _exx)
		{
			return null;
		}
		return null;
	}

	public static boolean isTokenBasedAuthentication(String authorizationHeader)
	{

		// Check if the Authorization header is valid
		// It must not be null and must be prefixed with "Bearer" plus a whitespace
		// The authentication scheme comparison must be case-insensitive
		return authorizationHeader != null
				&& authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase());
	}

	public static void abortWithUnauthorized(HttpServletRequest request)
	{

		// Abort the filter chain with a 401 status code response
		// The WWW-Authenticate header is sent along with the response
		// request.authenticate(HttpServletResponse.SC_UNAUTHORIZED)
		// .header(HttpServletResponse.SC_UNAUTHORIZED, AUTHENTICATION_SCHEME + "
		// realm=\"" + REALM + "\"").build());
	}
	
	public static String getPrintTrace(Exception _exx)
	{
		StringWriter writer = new StringWriter();
        PrintWriter printWriter= new PrintWriter(writer);
        _exx.printStackTrace(printWriter);
        
        return writer.toString();
        
	}

	public static void main(String[] args) {
		UserSecurity sec = new UserSecurity();
		sec.setFirstName("Onyekachi");
		sec.setLastName("Anyanwu");
		sec.setId(12);
		sec.setPassword("Wordpass");
		sec.setRole("user");
		sec.setUserId("Austin");
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		JsonParser parser = new JsonParser();
		JsonElement je = parser.parse(gson.toJson(sec));
		
		String json = gson.toJson(je);
		System.out.println(json);

	}

}
