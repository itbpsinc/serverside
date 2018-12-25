package com.itbps.fuelmgt.services;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Servlet implementation class Fuelservices
 */
@WebServlet("/fuelservices")
public class Fuelservices extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String VALID_USER = "user@domain.com:password";
	private static final String REALM = "com.itbps";
	private static final String AUTHENTICATION_SCHEME = "ITBPSINC ";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Fuelservices() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "No GET access.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get the Authorization header from the request
		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("ITBPSIC ")) {
            throw new ServletException("No JWT token found in request headers");
        }
		
		String name = request.getParameter("username");
		String pass = request.getParameter("password");
		response.setContentType("application/json");
		if (name == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String json = String.format("{\"error\": \"%s\"}", "Username is required");
			response.getWriter().print(json);
			return;
		}

		if (pass == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			String json = String.format("{\"error\": \"%s\"}", "Password is required");
			response.getWriter().print(json);
			return;
		}

		if ((name + ":" + pass).equals(VALID_USER)) {
			String token = createJWT("JWT-08", "ITPS INC", name, 10000);
			String json = String.format("{\"token\": \"%s\"}", token);
			response.getWriter().print(json);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			String json = String.format("{\"error\": \"%s\"}", "Username or Password is invalid");
			response.getWriter().print(json);
		}

	}

	private String createJWT(String id, String issuer, String payload, long ttlMillis) {
		// The JWT signature algorithm we will be using to sign the token
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("MySecret");

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setIssuer(issuer).setAudience(payload)
				.signWith(SignatureAlgorithm.HS256, apiKeySecretBytes);

		// if it has been specified, let's add the expiration
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();

	}

	private boolean isTokenBasedAuthentication(String authorizationHeader) {

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
		//request.authenticate(HttpServletResponse.SC_UNAUTHORIZED)
			//	.header(HttpServletResponse.SC_UNAUTHORIZED, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"").build());
	}

}
