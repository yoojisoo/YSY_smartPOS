package com.ysy.jwt.auth.handler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler implements Runnable{

	private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

	Thread thread1;
	Runnable runnable1;
	/* 사용자가(브라우저) 웹소켓 서버에 붙게되면 동작하는 메소드 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("############ afterConnectionEstablished connect ############## " );
		System.out.println("session.getId() ===== " + session.getId());
		System.out.println("session ===== " + session);
		CLIENTS.put(session.getId(), session);
		
		if(CLIENTS.size()==1) {
			runnable1 = new WebSocketHandler();
			thread1 = new Thread(runnable1);
			thread1.start();
		}
			
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("############ afterConnectionClosed close ############## " + session.getId());
		CLIENTS.remove(session.getId());
		if(CLIENTS.size()==0) {
			System.out.println("######################### thread close #####################");
			thread1.stop();
			runnable1 = null;
			thread1 = null;
		}
	}

	/* 사용자의 메시지를 받게되면 동작하는 메소드 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("message.getPayload() ========================"+message.getPayload());
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		final int  cnt = 0;
		while (CLIENTS.size()>0) {
			
			CLIENTS.entrySet().forEach(arg -> {
				try {
						System.out.println("arg.getKey() = ["+arg.getKey().toString() + "] arg.getValue() [" + arg.getValue() + "]"); 
						byte[] bytes = ("서버 session id =["+arg.getKey().toString()+"]").getBytes();
						arg.getValue().sendMessage(new TextMessage(bytes));
						
						
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				if (!arg.getKey().equals(id)) { // 같은 아이디가 아니면 메시지를 전달합니다.
//					try {
//						arg.getValue().sendMessage(message);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
			});
			try {
				thread1.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
