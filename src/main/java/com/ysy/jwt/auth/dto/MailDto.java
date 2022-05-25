package com.ysy.jwt.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailDto {
	private String email;
	private String key;
}
