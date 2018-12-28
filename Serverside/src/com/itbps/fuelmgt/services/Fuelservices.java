package com.itbps.fuelmgt.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;

import com.auth0.jwt.interfaces.Claim;
import com.itbps.fuelmgt.Authval;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

/**
 * Servlet implementation class Fuelservices
 */
@WebServlet("/fuelservices")
public class Fuelservices extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private final String VALID_USER = "Austin:password";
	private static final String REALM = "com.itbps";
	private static final String AUTHENTICATION_SCHEME = "ITBPSINC";
	private static final String SECRET = "ITBPSINC Woodbridge VA 22193";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Fuelservices()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
		// response.setContentType("text/plain");
		// response.sendError(HttpServletResponse.SC_FORBIDDEN, "No GET access.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Get the Authorization header from the request

		String authorizationHeader = request.getHeader("Authorization");

		if (!this.isTokenBasedAuthentication(authorizationHeader))
			throw new ServletException("No JWT token found in request headers");

		String oldtoken = authorizationHeader.substring(AUTHENTICATION_SCHEME.length());

		Claims claim = null;
		/*
		try
		{
			claim = this.decodeJWT(oldtoken);
		} catch (Exception _exx)
		{
			throw new ServletException("No JWT Invalid token found in request headers");
		}
        */
		
		String data = "";
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null)
		{
			builder.append(line);
		}

		data = builder.toString();
		JSONObject object = null;
		String name = null;
		String pass = null;

		try
		{
			object = new JSONObject(data);
			name = object.getString("loginid");
			pass = object.getString("password");
		} catch (Exception _exx)
		{
			_exx.printStackTrace();
		}

		response.setContentType("application/json");
		if (name == null)
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String json = String.format("{\"error\": \"%s\"}", "Username is required");
			response.getWriter().print(json);
			return;
		}

		if (pass == null)
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String json = String.format("{\"error\": \"%s\"}", "Password is required");
			response.getWriter().print(json);
			return;
		}

		if ((name + ":" + pass).equals(VALID_USER))
		{

			Authval auth = new Authval();
			auth.setExpiration(10000);
			auth.setId("JWT-09");
			;
			auth.setIssue("ITBPS Inc");
			;
			auth.setLoginid("Austin");
			auth.setName("Onyekachi Anyanwu");
			auth.setRole("Admin");

			String ntoken = "";

			try
			{
				ntoken = createJWT(auth);
			} catch (Exception _exx)
			{
				_exx.printStackTrace();
			}

			// String token = createJWT("JWT-09", "ITPS INC", name, 10000);

			// String newJson = String.format("{\"token\": \"%s\"}", token);
			JSONObject jsons = new JSONObject();
			try
			{
				jsons.put("token", ntoken);
			} catch (Exception _exx)
			{
				_exx.printStackTrace();
			}
			// String newJson = "token: " + token;
			response.getWriter().print(jsons.toString());
		} else
		{
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			String json = String.format("{\"error\": \"%s\"}", "Username or Password is invalid");
			response.getWriter().print(json);
		}

	}

	private String createJWT(Authval auth) throws Exception
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
		byte[] secret = this.SECRET.getBytes();
		String realsecret = secret + "";

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(realsecret);

		Claims claims = new DefaultClaims();
		claims.put("role", auth.getRole());
		claims.put("name", auth.getName());
		claims.put("loginid", auth.getLoginid());
		claims.setId(auth.getId());
		claims.setIssuer(auth.getIssue());
		claims.setIssuedAt(new Date());

		long expir = System.currentTimeMillis() + auth.getExpiration();
		;

		claims.setExpiration(new Date(expir));

		JwtBuilder builder = Jwts.builder().setHeader(headers).setClaims(claims).signWith(SignatureAlgorithm.HS256, apiKeySecretBytes);

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();

	}

	private String createJWT(String id, String issuer, String payload, long ttlMillis)
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
		byte[] secret = this.SECRET.getBytes();
		String realsecret = secret + "";

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
		return builder.compact();

	}

	public Claims decodeJWT(String jwt) throws Exception
	{

		// This line will throw an exception if it is not a signed JWS (as expected)
		byte[] secret = this.SECRET.getBytes();
		String realsecret = secret + "";
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(realsecret)).parseClaimsJws(jwt)
				.getBody();
		return claims;
	}

	private boolean isTokenBasedAuthentication(String authorizationHeader)
	{

		// Check if the Authorization header is valid
		// It must not be null and must be prefixed with "Bearer" plus a whitespace
		// The authentication scheme comparison must be case-insensitive
		return authorizationHeader != null
				&& authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}

	private void abortWithUnauthorized(HttpServletRequest request)
	{

		// Abort the filter chain with a 401 status code response
		// The WWW-Authenticate header is sent along with the response
		// request.authenticate(HttpServletResponse.SC_UNAUTHORIZED)
		// .header(HttpServletResponse.SC_UNAUTHORIZED, AUTHENTICATION_SCHEME + "
		// realm=\"" + REALM + "\"").build());
	}

}
