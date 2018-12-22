package com.example.dbtest.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "profile")
@Data
@NoArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "user_info_id")
    private int userInfoId;

    @Column(name = "location_id")//tasksテーブルの中の外部キーになっているカラムを指定　相手先は外部結合から自動的に探しに行く
    private int locationId;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "location_id",//locationテーブルの
            insertable = false, updatable = false)//このnameはProfile内のフィールドと重複してはならない
    private Location location; //後で修正


    @Column(name = "profile_name")
    private String profileName;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "updated")
    private LocalDateTime updated;
    
    

	public Profile(int userInfoId, int locationId, String profileName, String hobby,
			LocalDateTime updated) {
		this.userInfoId = userInfoId;
		this.locationId = locationId;
		this.profileName = profileName;
		this.hobby = hobby;
		this.updated = updated;
	}

	public Profile(int id, int userInfoId, int locationId, String profileName, String hobby, LocalDateTime updated) {
		this.id = id;
		this.userInfoId = userInfoId;
		this.locationId = locationId;
		this.profileName = profileName;
		this.hobby = hobby;
		this.updated = updated;
	}
    
    
    

}






