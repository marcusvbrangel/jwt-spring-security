package com.mvbr.jwtspringsecurity.service;

public interface EmailService {
    void send(String to, String subject, String body);
}

