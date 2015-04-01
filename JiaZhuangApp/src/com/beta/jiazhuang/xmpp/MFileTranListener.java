package com.beta.jiazhuang.xmpp;

import java.io.File;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import android.os.Handler;
import android.util.Log;

import com.beta.jiazhuang.bitmap.cache.CacheUtils;
import com.beta.jiazhuang.dao.ChattingPeopleDAO;
import com.beta.jiazhuang.dao.MessageDAO;
import com.beta.jiazhuang.entity.CommonMessage;
import com.beta.jiazhuang.main.MsgEume.MSG_CONTENT_TYPE;
import com.beta.jiazhuang.main.MsgEume.MSG_DERATION;
import com.beta.jiazhuang.main.MsgEume.MSG_STATE;
import com.beta.jiazhuang.mybase.MyBaseApplication;
import com.beta.jiazhuang.util.CustomConst;
import com.beta.jiazhuang.util.FileUtils;
import com.beta.jiazhuang.util.TypeConverter;

/**
 * 文件接收类
 * @author WHF
 *
 */
public class MFileTranListener implements FileTransferListener {
	
	private String hostId;
	
	private MessageDAO messageDAO = (MessageDAO)MyBaseApplication.getInstance().dabatases.get(CustomConst.DAO_MESSAGE);
	private ChattingPeopleDAO cPeopleDAO = (ChattingPeopleDAO)MyBaseApplication.getInstance().dabatases.get(CustomConst.DAO_CHATTING);
	
	public MFileTranListener(){
		this.hostId = MXmppConnManager.hostUid;
	}
	
	@Override
	public void fileTransferRequest(FileTransferRequest request) {
		final IncomingFileTransfer accept = request.accept();
		String uid = request.getRequestor().split("/")[0];
		String content = CacheUtils.getImagePath(MyBaseApplication.getInstance(), "receiveImage/" + TypeConverter.getUUID() + ".pilin");
		File file = new File(content);
		if(FileUtils.mkdirs(content)){
			try {
				///创建聊天会话
				Chat chat = MXmppConnManager.getInstance().getChatManager().createChat(uid, MXmppConnManager.getInstance().getChatMListener().new MsgProcessListener());
				accept.recieveFile(file);
				long mills = System.currentTimeMillis();
				CommonMessage message = new CommonMessage(uid,
						"",
						mills,
						"0.12km",
						content,
						MSG_STATE.RECEIVEING,
						MSG_CONTENT_TYPE.IMAGE,
						MSG_DERATION.RECEIVE,
						TypeConverter.nullStringDefaultValue(
								MyBaseApplication.friendsNames.get(uid.trim()), uid.split("@")[0]));
				
				long rowid = messageDAO.save(message, hostId);
				new updateStatusthread(mills,rowid, accept,uid).start();
				if(!MyBaseApplication.mJIDChats.containsKey(uid)){
					MyBaseApplication.mJIDChats.put(uid, chat);
				}
				//刷新消息列表
				if(!cPeopleDAO.peopleChatting(uid,hostId)){
					android.os.Message om = new android.os.Message();
					om.what = CustomConst.HANDLER_FRIEND_LIST_UPDATE; //HANDLER_FRIEND_LIST_ADD
					om.obj = uid;
					MyBaseApplication.getHandlers("FriendListActivity").get(0).sendMessage(om);
				}
				
//				handRefreshSession(uid);
				refreshChatDialog(uid, rowid);
				
			} catch (XMPPException e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 文件接收状态更新线程
	 * @author zg
	 *
	 */
	class updateStatusthread extends Thread{
		
		private long rowid;
		private IncomingFileTransfer accept;
		private String uid;
		private long mills;
		
		public updateStatusthread(long mills,long rowid,IncomingFileTransfer accept,String uid){
			this.rowid = rowid;
			this.accept = accept;
			this.uid = uid;
			this.mills = mills;
		}
		@Override
		public void run() {
			
			while(!accept.isDone()){
			}
			messageDAO.updateStateByRowid(rowid, hostId, 1);
			refreshChatImageMsg(uid, mills,CustomConst.HANDLER_MSG_FILE_SUCCESS);
			handRefreshSession(uid);
		}
		
	}
	
	///
	public static void handRefreshSession(String uid) {
		
		android.os.Message om = new android.os.Message();
		om.what = CustomConst.HANDLER_FRIEND_LIST_UPDATE;
		om.obj = uid;
		MyBaseApplication.getHandlers("FriendListActivity").get(0).sendMessage(om);
		
	}
	/**
	 * 刷新聊天窗口信息
	 * @param uid
	 */
	public void refreshChatDialog(String uid,long rowid){
		
		//聊天对话框内刷新
		List<Handler> handlers = MyBaseApplication.getHandlers(uid);
		
		for(Handler hand : handlers){
			Log.i("MReceiveChatListener", hand.getClass().toString().split("$")[0]);
			if(hand.getClass().toString().contains("ChatActivity")){
				handChatActivity(hand,rowid);
			}
		}
		
	}
	
	public void refreshChatImageMsg(String uid,long mills,int what){
		
		// 聊天对话框内刷新
		List<Handler> handlers = MyBaseApplication.getHandlers(uid);
		if(handlers == null) return;
		for (Handler hand : handlers) {

			Log.i("MReceiveChatListener",
					hand.getClass().toString().split("$")[0]);
			if (hand.getClass().toString().contains("ChatActivity")) {
				refreshImageMsg(hand, mills,what);
			}
		}
		
	}
	/**
	 * 更新图片消息状态
	 * @param handler
	 * @param mills
	 */
	public void refreshImageMsg(Handler handler,long mills,int what){
		
		android.os.Message osMsg = new android.os.Message();
		osMsg.what = what;
		osMsg.obj = mills;
		handler.sendMessage(osMsg);
		
	}
	
	/**
	 * 刷新聊天窗口信息
	 * @param handler
	 * @param mMsg
	 */
	public void handChatActivity(Handler handler,long mMsg){
		
		android.os.Message osMsg = new android.os.Message();
		osMsg.what = CustomConst.HANDLER_MGS_ADD;
		osMsg.obj = mMsg;
		handler.sendMessage(osMsg);
	}
	

}
