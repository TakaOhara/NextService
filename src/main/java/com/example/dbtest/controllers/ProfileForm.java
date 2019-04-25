package com.example.dbtest.controllers;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
    private String nickname;
    private String image;
    private String detail;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updated;

}
