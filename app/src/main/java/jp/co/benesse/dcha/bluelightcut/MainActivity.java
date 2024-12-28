package jp.co.benesse.dcha.bluelightcut;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button) findViewById(R.id.ButtonAddFilter)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FilterService.class);
                intent.putExtra("KEY", ((RadioButton) MainActivity.this.findViewById(((RadioGroup) MainActivity.this.findViewById(R.id.RadioGroup)).getCheckedRadioButtonId())).getText().toString());
                MainActivity.this.startService(intent);
            }
        });
        ((Button) findViewById(R.id.ButtonRemoveFilter)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.stopService(new Intent(MainActivity.this, FilterService.class));
            }
        });
    }
}
