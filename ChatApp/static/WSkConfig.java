package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration 
@EnableWebSocket
public class WSkConfig implements WebSocketConfigurer{ 
		
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) { 
		registry.addHandler(myMessageHandler(), "/chat"); 
	}
	
	@Bean
	public WebSocketHandler myMessageHandler() { 
		return new WSHandler(); 
    } 
	
}
	

