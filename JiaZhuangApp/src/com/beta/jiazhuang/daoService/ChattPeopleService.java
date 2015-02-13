package com.beta.jiazhuang.daoService;

import java.util.ArrayList;
import java.util.List;

import com.beta.jiazhuang.dao.MessageDAO;
import com.beta.jiazhuang.entity.OneFriendEntity;
import com.beta.jiazhuang.mybase.MyBaseApplication;
import com.beta.jiazhuang.util.CustomConst;

/**
 * @author zg
 * 
 * 后台服务程序，监听消息列表的好友未读消息
 *
 */
public class ChattPeopleService {
	
	private MessageDAO messageDAO;
	
	public ChattPeopleService(){
		messageDAO = (MessageDAO)MyBaseApplication.getInstance().dabatases.get(CustomConst.DAO_MESSAGE);
	}
	/**
	 * 获取消息列表的成员
	 * @param uids
	 * @return
	 */
	public List<OneFriendEntity> findAll(List<String> uids,String hostUid){
		List<OneFriendEntity> cPeoples = new ArrayList<OneFriendEntity>();
		for(String uid:uids){
			OneFriendEntity oneFriendEnt = findByUid(uid,hostUid);
			if(oneFriendEnt!=null){
				cPeoples.add(oneFriendEnt);
			}
			
		}
		return cPeoples;
		
	}
	
	//获取有未读消息的用户
	public OneFriendEntity findByUid(String uid,String hostUid){
		
		long count = messageDAO.findReceiveButNotReadByUid(uid,hostUid);
		OneFriendEntity oneFriendEnt = null;
//		oneFriendEnt = new OneFriendEntity(uid, count);
		return oneFriendEnt;
		
	}
 	
}
