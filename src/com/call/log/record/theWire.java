package com.call.log.record;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class theWire extends Service {
	receiver catcher;
	public MediaRecorder recorder;
	File audiofile;
	String name, phonenumber;
	String audio_format;
	public String Audio_Type;
	int audioSource;
	Context context;
	Timer timer;
	Boolean offHook = false, ringing = false;
	Toast toast;
	Boolean isOffHook = false;
	private boolean recordstarted = false;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		catcher = new receiver();
		context = getApplicationContext();
		registerReceiver(catcher, new IntentFilter(
				"android.intent.action.PHONE_STATE"));

		String out = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss")
				.format(new Date());
		File sampleDir = new File(Environment.getExternalStorageDirectory(),
				"/Test1");
		if (!sampleDir.exists()) {
			Toast.makeText(context, "dir doesn't exist", Toast.LENGTH_LONG)
					.show();

			sampleDir.mkdirs();
		}
		
		try {
			audiofile = File.createTempFile(out, ".mp3", sampleDir);
		} catch (IOException e) {
			
			
		}
		// String path = Environment.getExternalStorageDirectory()
		// .getAbsolutePath();

		recorder = new MediaRecorder();
		// recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);

		recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(audiofile.getAbsolutePath());
		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		recorder.start();
		recordstarted = true;

		return START_NOT_STICKY;
	}

	public class receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// SharedPreferences sharedPreferences = PreferenceManager
			// .getDefaultSharedPreferences(context.getApplicationContext());
			// boolean checkBoxValue = sharedPreferences.getBoolean("check",
			// true);
			// boolean warning = sharedPreferences.getBoolean("warning", true);

			TelephonyManager manager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			switch (manager.getCallState()) {

			case TelephonyManager.CALL_STATE_IDLE:
				Toast.makeText(context, "REJECT || DISCO", Toast.LENGTH_LONG)
						.show();
				if (recordstarted) {
					recorder.stop();
					recordstarted = false;

				}

				android.os.Process.killProcess(android.os.Process.myPid());
				context.unregisterReceiver(catcher);
				break;
			}
		}

	}

}
