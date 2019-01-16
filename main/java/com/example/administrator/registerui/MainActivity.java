package com.example.administrator.registerui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private TextView habit;
    private AutoCompleteTextView areaText;
    private EditText vipEdit, telEdit, birthdayEdit, emailEdit;
    private RadioGroup group;
    private Spinner addressSpinner;
    private CheckBox swimCheck, gymCheck;
    private ToggleButton togglebutton;
    private String str, str3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] counties = getResources().getStringArray(R.array.region_array);
        areaText = (AutoCompleteTextView) findViewById(R.id.areaText);
        areaText.setThreshold(1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, counties);
        areaText.setAdapter(adapter);

        togglebutton = (ToggleButton) findViewById(R.id.habitOpenToggle);
        togglebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(togglebutton.isChecked()) {
                    habit = (TextView) findViewById(R.id.habit);
                    swimCheck = (CheckBox) findViewById(R.id.swimCheck);
                    gymCheck = (CheckBox) findViewById(R.id.gymCheck);
                    habit.setVisibility(View.VISIBLE);
                    swimCheck.setVisibility(View.VISIBLE);
                    gymCheck.setVisibility(View.VISIBLE);
                } else {
                    habit = (TextView) findViewById(R.id.habit);
                    swimCheck = (CheckBox) findViewById(R.id.swimCheck);
                    gymCheck = (CheckBox) findViewById(R.id.gymCheck);
                    habit.setVisibility(View.GONE);
                    swimCheck.setVisibility(View.GONE);
                    gymCheck.setVisibility(View.GONE);
                }
            }
        });
    }

    public void btnClick(View view) {
        vipEdit = (EditText) findViewById(R.id.vipEdit);
        telEdit = (EditText) findViewById(R.id.telEdit);
        birthdayEdit = (EditText) findViewById(R.id.birthdayEdit);
        emailEdit = (EditText) findViewById(R.id.emailEdit);
        group = (RadioGroup) findViewById(R.id.group);
        addressSpinner = (Spinner) findViewById(R.id.addressSpinner);
        swimCheck = (CheckBox) findViewById(R.id.swimCheck);
        gymCheck = (CheckBox) findViewById(R.id.gymCheck);
        togglebutton = (ToggleButton) findViewById(R.id.habitOpenToggle);
        areaText = (AutoCompleteTextView) findViewById(R.id.areaText);
        RadioButton radioButton = (RadioButton) findViewById(group.getCheckedRadioButtonId());
        StringBuilder str2 = new StringBuilder();
        if (!vipEdit.getText().toString().isEmpty()) {
            str2.append(vipEdit.getText().toString());
        }
        if (!telEdit.getText().toString().isEmpty()) {
            str2.append("\n" + telEdit.getText().toString());
        }
        if (!birthdayEdit.getText().toString().isEmpty()) {
            str2.append("\n" + birthdayEdit.getText().toString());
        }
        if (!emailEdit.getText().toString().isEmpty()) {
            str2.append("\n" + emailEdit.getText().toString());
        }
        if(!radioButton.getText().toString().isEmpty()) {
            str2.append("\n" + radioButton.getText().toString());
        }
        if (!addressSpinner.getSelectedItem().toString().isEmpty()) {
            str2.append("\n" + addressSpinner.getSelectedItem().toString());
        }
        if (togglebutton.isChecked()) {
            if (swimCheck.isChecked()) {
                str2.append("\n" + swimCheck.getText().toString());
            }
            if (gymCheck.isChecked()) {
                str2.append("\n" + gymCheck.getText().toString());
            }
        }
        if (!areaText.getText().toString().isEmpty()) {
            str2.append("\n" + areaText.getText().toString());
        }
        Toast toast =  Toast.makeText(MainActivity.this, "自定义效果", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);	// 设置出现位置
        TextView text = new TextView(MainActivity.this);
        text.setText(str2);	// 设置文本内容
        text.setTextColor(getResources().getColor(R.color.white));	// 文本颜色
        text.setTextSize(20);	// 文本字体大小
        text.setWidth(500);		// 设置toast的大小
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);	// 设置文本居中
        text.setBackgroundColor(Color.rgb(64,158,255));	// 设置背景颜色
        toast.setView(text); // 将文本插入到toast里
        toast.show();	// 展示toast
    }
}