package com.coursework.clickboardbackend.utils;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketUserService {

    private final Map<String, String> connectedUsers = new ConcurrentHashMap<>();

    public void addUser(String sessionId, String userId) {
        connectedUsers.put(sessionId, userId);
    }

    public void removeUser(String sessionId) {
        connectedUsers.remove(sessionId);
    }

    public String getUserId(String sessionId) {
        return connectedUsers.get(sessionId);
    }

    public Map<String, String> getAllConnectedUsers() {
        return connectedUsers;
    }
}

