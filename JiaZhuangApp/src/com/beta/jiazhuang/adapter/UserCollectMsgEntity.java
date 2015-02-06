package com.beta.jiazhuang.adapter;


/**
 * @author zg
 * 用户收集信息的数据实体
 *
 */
public class UserCollectMsgEntity {

	//头像
	private String avatar;
	//生成日期
	private String date;
	//类型：1 文字，2 图片，3 语音
	private int type;
	//内容
	private Object content;
	
	public UserCollectMsgEntity(String avatar, String date, int type,
			Object content) {
		super();
		this.avatar = avatar;
		this.date = date;
		this.type = type;
		this.content = content;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
}
