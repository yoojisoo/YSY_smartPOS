package com.ysy.jwt.auth.handler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

	/* 사용자가(브라우저) 웹소켓 서버에 붙게되면 동작하는 메소드 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("############ afterConnectionEstablished connect ############## " );
		System.out.println("session.getId() ===== " + session.getId());
		System.out.println("session ===== " + session);
		CLIENTS.put(session.getId(), session);
		
		CLIENTS.entrySet().forEach(arg -> {
					try {
						for(int i = 0; i < 5 ; i++) {
							byte[] bytes = ("안녕하세요 서버에서 전송된 내용입니다.횟수 = "+(i+1)).getBytes();
							System.out.println("message 전송 횟수 = " + (i+1));
							arg.getValue().sendMessage(new TextMessage(bytes));
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		//			if (!arg.getKey().equals(id)) { // 같은 아이디가 아니면 메시지를 전달합니다.
		//				try {
		//					arg.getValue().sendMessage(message);
		//				} catch (IOException e) {
		//					e.printStackTrace();
		//				}
		//			}
				});
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("############ afterConnectionClosed close ############## " );
		CLIENTS.remove(session.getId());
	}

	/* 사용자의 메시지를 받게되면 동작하는 메소드 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String id = session.getId(); // 메시지를 보낸 아이디
		CLIENTS.entrySet().forEach(arg -> {
			
			try {
				for(int i = 0; i < 5 ; i++) {
					System.out.println("message 전송 횟수 = " + (i+1));
					arg.getValue().sendMessage(message);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			if (!arg.getKey().equals(id)) { // 같은 아이디가 아니면 메시지를 전달합니다.
//				try {
//					arg.getValue().sendMessage(message);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		});
	}
}
