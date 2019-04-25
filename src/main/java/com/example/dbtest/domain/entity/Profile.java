package com.example.dbtest.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * ログイン認証用のテーブルはUserDetailsのimplementsが必要
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="profile")
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
public class Profile{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "nickname")
	private String nickname;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "detail")
	private String detail;
	
    @Column(name = "updated")
    private LocalDateTime updated;
	
}
