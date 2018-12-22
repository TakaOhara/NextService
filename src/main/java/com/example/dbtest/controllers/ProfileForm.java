package com.example.dbtest.controllers;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Digits(integer = 1, fraction = 0, message = "問題が発生しました")
    private int locationId;
    
    @NotNull (message = "ニックネームを入力してください")
    @Size(min = 1, max = 20, message="タイトルは20文字以内で入力してください")
    private String profileName;

    @NotNull (message = "趣味を入力してください")
    @Size(min = 1, max = 20, message="タイトルは20文字以内で入力してください")
    private String hobby;

    public boolean isNewProfile;

}
