package jp.co.benesse.dcha.bluelightcut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FilterReceiver extends BroadcastReceiver {
    SharedPreferences sharedPref = null;

    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            this.sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            Intent intent2 = new Intent(context, FilterService.class);
            String colorB = this.sharedPref.getString("filterKey", "default");
            if (!colorB.equals("default")) {
                intent2.putExtra("KEY", colorB);
                context.startService(intent2);
            }
        }
    }
}
