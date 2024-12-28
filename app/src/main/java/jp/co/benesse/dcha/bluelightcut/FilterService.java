package jp.co.benesse.dcha.bluelightcut;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import java.util.HashMap;
import java.util.Map;

public class FilterService extends Service {
    private int colorB = 255;
    final Map<String, Double> colorMap = new HashMap<String, Double>() {
        {
            put("0%", Double.valueOf(0.0d));
            put("20%", Double.valueOf(0.2d));
            put("40%", Double.valueOf(0.4d));
            put("60%", Double.valueOf(0.6d));
            put("80%", Double.valueOf(0.8d));
            put("100%", Double.valueOf(1.0d));
        }
    };
    private int colorRG = 255;
    private String filterKey = null;
    private boolean fs = true;
    private View mView = null;
    private WindowManager mWindowManager = null;
    private SharedPreferences sharedPref = null;

    public int onStartCommand(Intent intent, int flags, int startId) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2006, AccessibilityEvent.TYPE_GESTURE_DETECTION_START, -3);
        this.mWindowManager = (WindowManager) getSystemService("window");
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        this.filterKey = intent.getStringExtra("KEY");
        this.colorB = (int) (255.0d * this.colorMap.get(this.filterKey).doubleValue());
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString("filterKey", this.filterKey);
        editor.commit();
        if (this.fs) {
            this.fs = false;
            this.mView = layoutInflater.inflate(R.layout.filter, (ViewGroup) null);
            this.mView.setBackgroundColor(Color.argb(80, this.colorRG, this.colorRG, this.colorB));
            this.mWindowManager.addView(this.mView, layoutParams);
        } else {
            this.mView.setBackgroundColor(Color.argb(80, this.colorRG, this.colorRG, this.colorB));
            this.mWindowManager.updateViewLayout(this.mView, layoutParams);
        }
        startForeground(startId, new Notification.Builder(this).setContentIntent(PendingIntent.getActivity(this, 0, intent, 0)).build());
        return 2;
    }

    public void onDestroy() {
        super.onDestroy();
        this.mWindowManager.removeView(this.mView);
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString("filterKey", "default");
        editor.commit();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
