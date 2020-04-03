// IOnNewMsgArrivedListener.aidl
package mvvm.com.git1;

import mvvm.com.git1.HelloMsg;

interface IOnNewMsgArrivedListener {
     void OnNewMsgArrived(in HelloMsg msg);
}
