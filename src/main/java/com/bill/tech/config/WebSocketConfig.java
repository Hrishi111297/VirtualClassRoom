package com.bill.tech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//	   @Override
//	    public void registerStompEndpoints(StompEndpointRegistry registry) {
//	        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:1234").withSockJS();
//	    }
//
//	    @Override
//	    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry registry) {
//	        registry.enableSimpleBroker("/topic");//subscriber topic/message @sendto
//	        registry.setApplicationDestinationPrefixes("/app");//using purpose we can use /app/mess if @messagemapping()
//	    }
	
	   @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:1234").withSockJS();
	    }

	    @Override
	    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry registry) {
	        registry.enableSimpleBroker("/topic");//subscriber topic/message @send
	        registry.setApplicationDestinationPrefixes("/app");//using purpose we can use /app/mess if @messagemapping()
	    }
}