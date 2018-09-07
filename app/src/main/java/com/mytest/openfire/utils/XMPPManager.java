package com.mytest.openfire.utils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPManager {
    private static XMPPManager xmppManager;
    private XMPPConnection connection;
//    private XMPPConnectionListener xmppConnectionListener;

    public XMPPManager() {
        init();
    }

    public static XMPPManager getInstance() {
        if (null == xmppManager){
            synchronized (XMPPManager.class){
                if (null == xmppManager){
                    xmppManager = new XMPPManager();
                }
            }
        }
        return xmppManager;
    }

    private void init(){
        if (null == connection){
            ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration("127.0.0.1", 5222);
            /** 是否启用压缩,压缩可以省流量 */
            connectionConfiguration.setCompressionEnabled(true);
            /** 是否启用安全验证 */
            connectionConfiguration.setSASLAuthenticationEnabled(false);
            /** 是否启用调试 */
            connectionConfiguration.setDebuggerEnabled(false);
            connectionConfiguration.setReconnectionAllowed(true);
            connectionConfiguration.setRosterLoadedAtLogin(true);

            //创建connection连接
            connection = new XMPPConnection(connectionConfiguration);
            //建立连接
            try {
                connection.connect();
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否连接
     * */
    public boolean isConnection(){
       return connection != null && connection.isConnected();
    }

    /**
     * 判断连接是否连接，是否通过身份认证
     * */
    public boolean isAuthenticated(){
        return connection != null && connection.isConnected() && connection.isAuthenticated();
    }

    /**
     * 关闭连接
     * */
    public void closeConnection(){
        if (null != connection){
//            connection.removeConnectionListener();
            if (connection.isConnected()){
                connection.disconnect();
                connection = null;
            }
        }
    }


}
