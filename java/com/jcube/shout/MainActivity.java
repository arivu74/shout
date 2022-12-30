package com.jcube.shout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SMSReceiver smsReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestSmsPermission();
/*
        Context c = getApplicationContext();
        if ( CheckServiceAlreadyRunning(c) == false){
            Intent intentx = new Intent(c, SMSReceiver.class);
            c.startForegroundService(intentx);
        }
*/

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Toast.makeText(this, "this is inside if", Toast.LENGTH_LONG).show();
            smsReceiver = new SMSReceiver();
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            //intentFilter.addAction(Telephony.Sms.Intents.DATA_SMS_RECEIVED_ACTION);
            intentFilter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);
        }
*/
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ( requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"SMS Read Permission Granted",Toast.LENGTH_LONG ).show();
            }
            else{
                Toast.makeText(this, "SMS Read Permission Declined",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void requestSmsPermission() {
        String permission1 = Manifest.permission.RECEIVE_SMS;
        String[] permission_list = new String[1];
        permission_list[0] = permission1;

        int grant = ActivityCompat.checkSelfPermission(this, permission1);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
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