// IRemoteService.aidl
package mvvm.com.git1;

import mvvm.com.git1.HelloMsg;
import mvvm.com.git1.IOnNewMsgArrivedListener;
interface IRemoteService {
    HelloMsg sayHello();// 服务端 返回 语言
    List<HelloMsg> getMsgList(); // 返回 服务端 全部 语言总和
    void addMsg(in HelloMsg msg);// 客户端新增的语言
    void registerListener(IOnNewMsgArrivedListener listener); //注册 新语言来时的事件
    void unregisterListener(IOnNewMsgArrivedListener listener); //解除 新语言来时的事件
}
