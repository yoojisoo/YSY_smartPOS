package com.ysy.jwt.auth.model.naver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverProfile {

	public String resultcode;
	public String message;
	public Response response;
	@Data
	public static class Response {
		public String id;
		public String email;
		public String mobile;
		public String mobile_e164;
		public String name;

	}
}
