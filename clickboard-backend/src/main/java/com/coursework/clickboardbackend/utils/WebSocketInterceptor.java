package com.coursework.clickboardbackend.utils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketInterceptor implements ChannelInterceptor {

    private final WebSocketUserService userService;

    public WebSocketInterceptor(WebSocketUserService userService) {
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        switch (accessor.getCommand()) {
            case CONNECT:
                String sessionId = accessor.getSessionId();
                String userId = accessor.getUser().getName(); // Берем ID из Principal
                userService.addUser(sessionId, userId);
                break;

            case DISCONNECT:
                userService.removeUser(accessor.getSessionId());
                break;
        }

        return message;
    }
}

