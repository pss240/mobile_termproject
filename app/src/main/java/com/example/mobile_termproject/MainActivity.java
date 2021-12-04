package com.example.mobile_termproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String player1_penalty,player2_penalty = new String();
    LinearLayout start_lay,input_lay,result_lay;
    boolean check_input = false;
    TextView tvInput;
    EditText etInput;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            input_lay.setVisibility(View.GONE);
            result_lay = (LinearLayout) findViewById(R.id.lay_result);
            result_lay.setVisibility(View.VISIBLE);
            TextView tv_result = (TextView) findViewById(R.id.tv_result);
            TextView tv_result2 = (TextView) findViewById(R.id.tv_result2);
            TextView tv_penalty = (TextView) findViewById(R.id.tv_penalty);
            int activity_result = data.getIntExtra("activity_result",0);
            if(activity_result==1){
                tv_result.setText("1P WIN!!!");
                tv_result2.setText("  2P가 당할 벌칙은??  ");
                tv_penalty.setText(player1_penalty);
            }else if(activity_result==2){
                tv_result.setText("2P WIN!!!");
                tv_result2.setText("  1P가 당할 벌칙은??  ");
                tv_penalty.setText(player2_penalty);
            }
            else{
                System.out.println("error!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            Button btnRestart = (Button) findViewById(R.id.btnRestart);
            btnRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getApplicationContext();
                    PackageManager packageManager = context.getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
                    ComponentName componentName = intent.getComponent();
                    Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                    context.startActivity(mainIntent);
                    Runtime.getRuntime().exit(0);
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        ImageButton btnStart = (ImageButton) findViewById(R.id.btnStart);
        start_lay = (LinearLayout) findViewById(R.id.start_lay);
        input_lay = (LinearLayout) findViewById(R.id.input_lay);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_lay.setVisibility(View.GONE);
                input_lay.setVisibility(View.VISIBLE);
            }
        });

        ImageButton btnOK = (ImageButton) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInput = (TextView) findViewById(R.id.tvInput);
                etInput = (EditText) findViewById(R.id.etInput);
                if(etInput.getText().toString().replace(" ","").equals("")) {
                    Toast.makeText(getApplicationContext(), "벌칙을 입력해주세요!!", Toast.LENGTH_SHORT).show();
                }else {
                    if (check_input == true) {
                        player1_penalty = etInput.getText().toString();
                        check_input=false;
                        Intent intent = new Intent(getApplicationContext(),SubActivity.class);
                        startActivityForResult(intent,0);
                    } else {
                        tvInput.setText("  1p가 당할 벌칙은??  ");
                        player2_penalty = etInput.getText().toString();
                        etInput.setText("");
                        check_input=true;
                    }
                }
            }
        });



    }
}