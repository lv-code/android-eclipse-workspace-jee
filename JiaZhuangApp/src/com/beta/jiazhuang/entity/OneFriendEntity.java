package com.beta.jiazhuang.entity;

import java.io.Serializable;

public class OneFriendEntity extends Entity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static final String UID = "uid";
	
	public static final String AVATAR = "avatar";
	
	public static final String GROUP_ROLE = "group_role";
	
	public static final String INDUSTRY = "industry";
	
	public static final String NAME = "name";
	
	public static final String SIGN = "sign";
	// 未读消息数
	private long msgNotReadCount;
	
	private String uid;// ID
	private String avatar;// 头像
	private String industry;// 行业

	private String name;// 姓名
	private int state;//在线状态 0-在线，1-隐身
	
	public OneFriendEntity(String uid, String avatar, String industry, String name, int state, long msgNotReadCount) {
		super();
		this.uid = uid;
		this.avatar = avatar;
		this.industry = industry;
		this.name = name;
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

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public long getMsgNotReadCount() {
		return msgNotReadCount;
	}

	public void setMsgNotReadCount(long msgNotReadCount) {
		this.msgNotReadCount = msgNotReadCount;
	}
	
}
