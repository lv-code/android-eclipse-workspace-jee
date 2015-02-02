package com.beta.util;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.OfflineMessageManager;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;

import android.content.Context;

import com.beta.mybase.MyBaseApplication;
import com.beta.xmpp.ReConnectionListener;

/**
 * @author zg
 * 
 * 链接smack服务器
 *
 */
public class ClientConServer { 
	private AccountManager accountManager;
	
	private ChatManager chatManager;
	
	private OfflineMessageManager offlineMessageManager;
	
	private ConnectionListener connectionListener;
	
//	private MReceiveChatListener chatManagerListener;
	
	public  FileTransferManager transferManager;
	
	private static String SERVER_IP = "192.168.9.126";
	
	private static String HOST = "AY140326090233737f4cZ";
    private static int PORT=5222;  
    private Context context;  
    public ClientConServer(Context context){  
        this.context=context;  
  
    }  
      
    public boolean login(String a,String p){  
        ConnectionConfiguration config = new ConnectionConfiguration(SERVER_IP, PORT);  
        /** 是否启用安全验证 */  
        config.setSASLAuthenticationEnabled(false);  
        /** 是否启用调试 */  
        //config.setDebuggerEnabled(true);  
        /** 创建connection链接 */  
        XMPPConnection connection = new XMPPConnection(config);  
        try {  
            /** 建立连接 */  
            connection.connect();  
            /** 登录*/  
            connection.login(a, p);  
            /** 开启读写线程，并加入到管理类中*/  
            //ClientSendThread cst=new ClientSendThread(connection);  
            //cst.start();  
            //ManageClientThread.addClientSendThread(a, cst);  
            
			connection = new XMPPConnection(config);
			config = new ConnectionConfiguration(SERVER_IP, PORT,
					HOST);
			config.setSASLAuthenticationEnabled(false);
			config.setReconnectionAllowed(true);
			config.setSendPresence(false);
			config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
			connection = new XMPPConnection(config);
			connection.connect();
			accountManager = connection.getAccountManager();
			chatManager = connection.getChatManager();
			transferManager = new FileTransferManager(connection);
			connectionListener = new ReConnectionListener(MyBaseApplication.getInstance());
			connection.addConnectionListener(connectionListener);
            return true;  
        } catch (XMPPException e) {  
            e.printStackTrace();  
        }  
        return false;  
     }  
}  