package com.jcube.shout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.AlarmClock;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    private static String sms = "android.provider.Telephony.SMS_RECEIVED";
     MediaPlayer mp = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        final AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        final Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        if(mp == null) {
            mp = MediaPlayer.create(context,R.raw.my_alarm);
            mp.setVolume(1,1);
        }
        if (intent.getAction().equals(sms) )
        {
            Intent intentx = new Intent(context, SMSReceiver.class);
            context.startService(intentx);

            Bundle bundle = intent.getExtras();
            Object[] obj = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[obj.length];
            for(int i=0;i<obj.length;i++){
                messages[i] = SmsMessage.createFromPdu((byte[])obj[i]);
            }
            String msg = messages[0].getMessageBody();

            if ( msg.equals("Shout") ){
                Toast.makeText(context, "Shouting ... ", Toast.LENGTH_SHORT).show();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        int i=0;
                        mp.setVolume(.12f,.12f);
                        mp.start();
                        try
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(mp.getDuration(), VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                v.vibrate(mp.getDuration());
                            }
                            Thread.sleep((mp.getDuration()+1000));
                        } catch (InterruptedException e)
                        {
                            ; //e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        }
    }
}
