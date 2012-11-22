package com.clover.shakeac.helper;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.clover.shakeac.model.Result;
import com.clover.shakeac.model.User;


public class ParseUtil {
	
	private static ObjectMapper mapper;
	
	public static Result<User> parseUser(String json) {
		JsonNode root = getNode(json);
		if (root != null) {
			Result<User> result = new Result<User>();
			result.setStatus(root.get("status").asInt());
			result.setMessage(root.get("message").asText());
			JsonNode content = root.get("content");
			
			if (content == null) {
				result.setModel(null);
			} else {
				User user = new User();
				user.setName(content.get("name").asText());
				user.setId(content.get("id").asInt());
				user.setSignature(content.get("signature").asText());
				user.setLevel(content.get("level").asInt());
				user.setSource(content.get("source").asInt());
				user.setExp(content.get("exp").asInt());
				user.setUdid(content.get("udid").asText());
				user.setAvatar(content.get("avatar").asText());
				
				result.setModel(user);
				return result;
			}
		}
		return null;
	}
	
	private static JsonNode getNode(String json) {
		try {
			if (mapper == null) {
				mapper = new ObjectMapper();
			}
			JsonNode root = mapper.readTree(json);
			return root;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
