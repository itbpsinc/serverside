package com.itbps.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ResponseBuilder {

	final static JsonParser parser = new JsonParser();
	public static Response createResponse(Response.Status status) {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("message", status.toString());
		} catch (JSONException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return Response.status(status).entity(jsonObject.toString()).build();
	}

	public static Response createResponse(Response.Status status, String message) {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("message", message);
		} catch (JSONException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return Response.status(status).entity(jsonObject.toString()).build();
	}

	public static Response createResponse(Response.Status status, JSONObject json) throws JSONException {
		return Response.status(status).entity(json.toString()).build();
	}

	public static Response createResponse(Response.Status status, Object obj) throws JSONException 
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		
		JsonElement je = parser.parse(gson.toJson(obj));
		
		String json = gson.toJson(je);
		
		JSONObject jobj = new JSONObject(json);
		String key = jobj.keys().next().toString();
		
		JSONObject jobj2 = new JSONObject();
		jobj2.put("data", jobj.get(key));

		
		return Response.status(status).entity(jobj2.toString()).build();
	}
	
	
	public static Response createResponse(Response.Status status, List<JSONObject> json) throws JSONException {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < json.size(); i++) {
			jsonArray.put(json.get(i).toString());
		}

		return Response.status(status).entity(jsonArray.toString()).build();
	}

	public static Response createResponse(Response.Status status, Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();

		try {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}
		} catch (JSONException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Response.Status.INTERNAL_SERVER_ERROR)
					.build();
		}

		return Response.status(status).entity(jsonObject.toString()).build();
	}

}
