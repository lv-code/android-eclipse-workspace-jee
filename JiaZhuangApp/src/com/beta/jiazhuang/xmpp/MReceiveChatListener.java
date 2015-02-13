package com.beta.jiazhuang.xmpp;

import java.io.FileNotFoundException;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.os.Handler;
import android.util.Log;

import com.beta.jiazhuang.dao.ChattingPeopleDAO;
import com.beta.jiazhuang.dao.MessageDAO;
import com.beta.jiazhuang.entity.CommonMessage;
import com.beta.jiazhuang.main.MsgEume.MSG_CONTENT_TYPE;
import com.beta.jiazhuang.main.MsgEume.MSG_DERATION;
import com.beta.jiazhuang.main.MsgEume.MSG_STATE;
import com.beta.jiazhuang.mybase.MyBaseApplication;
import com.beta.jiazhuang.util.CustomConst;
import com.beta.jiazhuang.util.TypeConverter;


/**
 * @author zg
 * 
 * 聊天接受监听器
 *
 */
public class MReceiveChatListener implements ChatManagerListener{

	XmppFriendManager xManager;

	MessageDAO messageDAO;
	ChattingPeopleDAO cPeopleDAO;
	
	String hostUid;
	
	public MReceiveChatListener() {

		this.xManager = XmppFriendManager.getInstance();
		this.hostUid = MXmppConnManager.hostUid;
		
		messageDAO = (MessageDAO)MyBaseApplication.getInstance().dabatases.get(CustomConst.DAO_MESSAGE);
		cPeopleDAO = (ChattingPeopleDAO)MyBaseApplication.getInstance().dabatases.get(CustomConst.DAO_CHATTING);
	}
	
	@Override
	public void chatCreated(Chat chat, boolean isExist){
		if(chat.getListeners().isEmpty()){
			chat.addMessageListener(new MsgProcessListener());
		}
		
	}
	
	public class MsgProcessListener implements MessageListener{
			
			@Override
			public void processMessage(Chat chat, Message msg) {
				//与每个用户的会话只应该有一个消息监听器
				if(chat.getListeners().toArray().length>1){
					chat.removeMessageListener(this);
					return;
				}
				
				String uid = msg.getFrom().split("/")[0];
				CommonMessage mMsg = null;
				long rowid = 0;
				
				try {
					mMsg = new CommonMessage(uid.trim(), xManager
							.getUserIconAvatar(uid),
							System.currentTimeMillis(), "0.32km",
							msg.getBody(), MSG_STATE.ARRIVED,
							MSG_CONTENT_TYPE.TEXT, MSG_DERATION.RECEIVE,
							TypeConverter.nullStringDefaultValue(
									MyBaseApplication.friendsNames.get(uid.trim()), uid.split("@")[0]));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (XMPPException e) {
					e.printStackTrace();
				}
					
				rowid = messageDAO.save(mMsg,hostUid);
				if(!MyBaseApplication.mJIDChats.containsKey(uid)){
					MyBaseApplication.mJIDChats.put(uid, chat);
				}
				//刷新消息列表
//				if(!cPeopleDAO.peopleChatting(uid,hostUid)){
//					android.os.Message om = new android.os.Message();
//					om.what = CustomConst.HANDLER_CHATPEOPLE_LIST_ADD;
//					om.obj = uid;
//					MyBaseApplication.getHandlers("FriendListActivity").get(0).sendMessage(om);
//				}
				
				handRefreshSession(uid);
				
				//聊天对话框内刷新
				List<Handler> handlers = MyBaseApplication.getHandlers(uid);
				
				for(Handler hand : handlers){
					
					Log.i("MReceiveChatListener", hand.getClass().toString().split("$")[0]);
					if(hand.getClass().toString().contains("ChatActivity")){
						handChatActivity(hand,rowid);
					}
				}
			}
			
			//
			public void handRefreshSession(String uid) {
				
				android.os.Message om = new android.os.Message();
				om.what = CustomConst.HANDLER_FRIEND_LIST_UPDATE;
				om.obj = uid;
				
				MyBaseApplication.getHandlers("FriendListActivity").get(0).sendMessage(om);
				
			}

			/**
			 * 刷新聊天窗口信息
			 * @param handler
			 * @param mMsg
			 */
			public void handChatActivity(Handler handler,long mMsg){
				
				android.os.Message osMsg = new android.os.Message();
				osMsg.what = 0;
				osMsg.obj = mMsg;
				handler.sendMessage(osMsg);
			}
	}
	
}
