package com.jcube.shout;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class ReRegister extends BroadcastReceiver {
    private static String bootcompleted = "android.intent.action.BOOT_COMPLETED";
    SMSReceiver smsReceiver = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        //if ( CheckServiceAlreadyRunning(context) == false){
            Intent intentx = new Intent(context, SMSReceiver.class);
            context.startService(intentx);
        //}


       // Toast.makeText(context, "Reboot hook", Toast.LENGTH_SHORT).show();
       // smsReceiver = new SMSReceiver();
       // IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
       // //intentFilter.addAction(Telephony.Sms.Intents.DATA_SMS_RECEIVED_ACTION);
       // intentFilter.addAction("Telephony.Sms.Intents.SMS_RECEIVED_ACTION");
       // context.registerReceiver(smsReceiver, intentFilter);
    }
    public boolean CheckServiceAlreadyRunning(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service:activityManager.getRunningServices(Integer.MAX_VALUE)){
            String s1 = service.service.getClassName();
            String s2 = SMSReceiver.class.getName();
            if (s1.equals(s2)) return true;
        }
        return false;
    }
}
