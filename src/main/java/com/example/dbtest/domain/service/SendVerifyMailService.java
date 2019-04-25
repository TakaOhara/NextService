package com.example.dbtest.domain.service;

import com.example.dbtest.domain.entity.User;

public interface SendVerifyMailService {
    void execute(User user);
}