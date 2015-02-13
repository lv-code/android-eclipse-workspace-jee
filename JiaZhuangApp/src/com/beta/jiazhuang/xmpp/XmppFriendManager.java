package com.beta.jiazhuang.xmpp;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.packet.VCard;

import com.beta.jiazhuang.dao.MessageDAO;
import com.beta.jiazhuang.entity.OneFriendEntity;
import com.beta.jiazhuang.mybase.MyBaseApplication;
import com.beta.jiazhuang.util.CustomConst;
import com.beta.jiazhuang.util.TypeConverter;

/**
 * @author zg
 * 
 * 好友列表管理类
 *
 */
public class XmppFriendManager {
	
	XMPPConnection connection = MyBaseApplication.xmppConnection;
	
	Roster roster;
	MessageDAO messageDAO;
	VCard vCard;
	
//	ImageCache imageCache;
	
	static XmppFriendManager xmppFriendManager;
	
	private XmppFriendManager(){
		xmppFriendManager = this;
	}
	
	public static XmppFriendManager getInstance(){
		if(xmppFriendManager == null){
			xmppFriendManager = new XmppFriendManager();
		}
		return xmppFriendManager;
	}
	
	/**
	 * 获取好友列表
	 * @return
	 * @throws FileNotFoundException
	 * @throws XMPPException
	 */
	public List<OneFriendEntity> getFriends(){
		
		List<OneFriendEntity> friends = new ArrayList<OneFriendEntity>();
		
		if(connection!=null){
			friends = getFriendOnHasNetWork(friends);
		}
		return friends;
		
	}
	/**
	 * 获取群组
	 * @param roster
	 * @return
	 */
	public List<RosterGroup> getGroups(Roster roster){
		
		List<RosterGroup> groups = new ArrayList<RosterGroup>();
		Collection<RosterGroup> rosterGroups = roster.getGroups();
		Iterator<RosterGroup> ite = rosterGroups.iterator();
		while(ite.hasNext()){
			groups.add(ite.next());
		}
		
		return groups;
	}
	/**
	 * 获取某个群组的用户
	 * @param roster 角色
	 * @param groupName 群组名
	 * @return
	 */
	public List<RosterEntry> getEntiesByGroup(Roster roster,String groupName){
		List<RosterEntry> enties = new ArrayList<RosterEntry>();
		RosterGroup group = roster.getGroup(groupName);
		Collection<RosterEntry> ent = group.getEntries();
		Iterator<RosterEntry> ite = ent.iterator();
		while(ite.hasNext()){
			enties.add(ite.next());
		}
		return enties;
	}
	
	/**
	 * 获取用户信息
	 * @param friends
	 * @return
	 */
	public List<OneFriendEntity> getFriendOnHasNetWork(List<OneFriendEntity> friends){
		// 中文意为“名册”
		roster = connection.getRoster();
		roster.addRosterListener(new MRosterListener());
		List<RosterGroup> groups = getGroups(roster);
		OneFriendEntity friend;
		messageDAO = (MessageDAO)MyBaseApplication.getInstance().dabatases.get(CustomConst.DAO_MESSAGE);
		for(RosterGroup group : groups){
			
			List<RosterEntry> entries = getEntiesByGroup(roster, group.getName());
			for(RosterEntry entry:entries){
				
				String avatar = null;
				try {
					// 获取头像文件名
					avatar = getUserIconAvatar(entry.getUser());
				} catch (XMPPException e) {
					e.printStackTrace();
					return friends;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return friends;
				}
				MyBaseApplication.friendsNames.put(entry.getUser(), TypeConverter.nullStringDefaultValue(vCard.getNickName(), entry.getName()));
				// 实例化好友类
				friend = new OneFriendEntity(entry.getUser(),
						avatar,
						"设计师", //职业"姓名", //
						TypeConverter.nullStringDefaultValue(vCard.getNickName(), entry.getName()),
						roster.getPresence(entry.getUser()).isAvailable()?0:1,
						messageDAO.findReceiveButNotReadByUid(entry.getUser(),MyBaseApplication.xmppConnection.getUser())
						);
				friends.add(friend);
			}
			
		}
		return friends;
	}
	
	/**
	 * 获取用户信息类
	 * @param user 用户UID
	 * @return
	 */
	public VCard getVCard(String user){
		
		vCard = new VCard();
		ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp",  
				new org.jivesoftware.smackx.provider.VCardProvider());
		try {
			vCard.load(connection, user);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		
		return vCard;
		
	}
	
	/**
	 * 获取指定好友ID的头像，保存到缓存目录
	 * @param user
	 * @return
	 * @throws XMPPException
	 * @throws FileNotFoundException
	 */
	public String getUserIconAvatar(String user) throws XMPPException, FileNotFoundException{
		vCard = getVCard(user);
		
		if(vCard == null){
			return null;
		}
		String fileName = "tmp_touxiang01.png";
		return fileName;
	}
	
	public class MRosterListener implements RosterListener {
		
		@Override
		public void presenceChanged(Presence presence) {
			
			String uid = presence.getFrom().split("/")[0];
			
			int type = 0;
			
			if(presence.getType().equals(Presence.Type.unavailable)){
				
				//删除与该人的当前会话，防止下次再通讯时导致会话不一致
				MyBaseApplication.mJIDChats.remove(uid);
				
				type = CustomConst.USER_STATE_OFFLINE;
				
			}else if(presence.getType().equals(Presence.Type.available)){
				
				Presence.Mode mode = presence.getMode();
				
				if(mode.equals(Presence.Mode.chat)){
					
					type = CustomConst.USER_STATE_Q_ME;
					
				}else if(mode.equals(Presence.Mode.dnd)){
					
					type = CustomConst.USER_STATE_BUSY;
					
				}else if(mode.equals(Presence.Mode.away)){
					
					type = CustomConst.USER_STATE_AWAY;
					
				}else{
					
					type = CustomConst.USER_STATE_ONLINE;
					
				}
			}
			
//			FriendsFragment fragment = (FriendsFragment)((MainFragmentActivity)context).getFragments()[2];
//			
//			Message message = new Message();
//			
//			message.what = 0;
//			
//			message.obj = uid;
//			
//			message.arg1 = type;
//			
//			fragment.getHandler().sendMessage(message);
			
		}
		
		@Override
		public void entriesUpdated(Collection<String> arg0) {
			
		}
		
		@Override
		public void entriesDeleted(Collection<String> arg0) {
			
		}
		
		@Override
		public void entriesAdded(Collection<String> arg0) {
		}
	}
	
}
