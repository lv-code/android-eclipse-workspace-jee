package com.beta.jiazhuang.entity;

import java.io.Serializable;

import com.beta.main.R;

public class FriendEntity extends Entity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static final String UID = "uid";
	
	public static final String AVATAR = "avatar";
	
	public static final String GROUP_ROLE = "group_role";
	
	public static final String INDUSTRY = "industry";
	
	public static final String RELATION = "relation";
	
	public static final String MULTIPIC = "multipic";
	
	public static final String NAME = "name";
	
	public static final String TIME = "time";
	
	public static final String SIGN = "sign";
	
	private String uid;// ID
	private String avatar;// 头像
	private int isVip;// Vip 0-不是Vip,1-是Vip
	private int isGroupRole;// 群成员 0-未加入群,1-群主,2-群成员
	private String industry;// 行业
	private int device;// 设备 0-未绑定,1-Android,2-IPhone
	private int isRelation;// 好友关系 0-非好友,1-好友
	private int isMultipic;// 照片 0-无照片,1-有照片

	private String name;// 姓名
	private int gender;// 性别 0-女，1-男
	private int genderId;// 性别对应的图片资源ResId
	private int genderBgId;// 性别对应的背景资源ResId
	private int age;// 年龄
	private String distance;// 距离
	private String time;// 时间
	private String sign;// 签名
	private int state;//在线状态 0-在线，1-隐身
	
	public FriendEntity(String uid, String avatar, int isVip, int isGroupRole,
			String industry, String name, String sign,int state) {
		super();
		this.uid = uid;
		this.avatar = avatar;
		this.isVip = isVip;
		this.isGroupRole = isGroupRole;
		this.industry = industry;
		this.name = name;
		this.sign = sign;
		this.state = state;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	public int getIsGroupRole() {
		return isGroupRole;
	}

	public void setIsGroupRole(int isGroupRole) {
		this.isGroupRole = isGroupRole;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}


	public int getDevice() {
		return device;
	}

	public void setDevice(int device) {
		this.device = device;
	}

	public int getIsRelation() {
		return isRelation;
	}

	public void setIsRelation(int isRelation) {
		this.isRelation = isRelation;
	}

	public int getIsMultipic() {
		return isMultipic;
	}

	public void setIsMultipic(int isMultipic) {
		this.isMultipic = isMultipic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getGenderId() {
		return genderId;
	}

	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}

	public int getGenderBgId() {
		return genderBgId;
	}

	public void setGenderBgId(int genderBgId) {
		this.genderBgId = genderBgId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
	
}
