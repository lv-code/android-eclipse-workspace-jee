package com.beta.xmpp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.beta.entity.ChatMessage;
import com.beta.main.MsgEume.MSG_STATE;
import com.beta.main.OfflineMsgReceiver;
import com.beta.main.R;
import com.beta.util.TypeConverter;

public class OfflineMessageSendBrocast {
	
	public static Map<String,ArrayList<Message>> getOfflineMegs(){
		
		Map<String,ArrayList<Message>> offlines = new HashMap<String,ArrayList<Message>>();
		
		try {
			Iterator<Message> ites = MXmppConnManager.getInstance().getOffMsgManager().getMessages();
			
			while(ites.hasNext()){
				
				Message message = ites.next();
				
				String fromUser = message.getFrom().split("/")[0];
				
				if(offlines.containsKey(fromUser)){
					
					offlines.get(fromUser).add(message);
					
				}else{
					
					ArrayList<Message> temp = new ArrayList<Message>();
					
					temp.add(message);
					
					offlines.put(fromUser, temp);
					
				}
				
			}
			
		} catch (XMPPException e) {
			e.printStackTrace();
			return null;
		}
		
		return offlines;
		
	}
	
	public static void sendBrocast(Context context,Map<String,ArrayList<Message>> messages){
		
		if(messages != null && messages.size() > 0){
		
			Set<String> keys = messages.keySet();
			
			Iterator<String> keyIte = keys.iterator();
			
			Intent intent = new Intent(context, OfflineMsgReceiver.class);
			
			ChatMessage chatListUserInfo = null;
			
			while(keyIte.hasNext()){
				
				String key = keyIte.next();
				
				chatListUserInfo = new ChatMessage(R.drawable.ic_launcher,
						key, 
						TypeConverter.formatDate(new Date(), "MM-dd HH:mm:ss"), 
						messages.get(key).get(0).getBody("UTF-8"), 
						MSG_STATE.ARRIVED);
				
				Bundle bundle = new Bundle();
				
				bundle.putSerializable("user", chatListUserInfo);
				
				intent.putExtra("user", bundle);
				
				context.sendBroadcast(intent);
			}
			
		}
		try {
			
			MXmppConnManager.getInstance().getOffMsgManager().deleteMessages();
			
			MXmppConnManager.getInstance().setPresence(0);
			
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
