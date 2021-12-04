package com.example.mobile_termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class SubActivity extends AppCompatActivity {
    float initX,initY,result;;
    ImageView test1,test2,iv_ball1,iv_ball2;
    boolean check_move1,check_move2,check_attack,flag_move=false;
    boolean right1,left2=true;
    int player2_life=3;
    int player1_life=3;
    float x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_sub);
        test1 = (ImageView) findViewById(R.id.test1);
        test2 = (ImageView) findViewById(R.id.test2);
        iv_ball1 = (ImageView) findViewById(R.id.iv_ball1);
        iv_ball2 = (ImageView) findViewById(R.id.iv_ball2);
        LinearLayout lay_move1 = (LinearLayout) findViewById(R.id.lay_move1);
        LinearLayout lay_move2 = (LinearLayout) findViewById(R.id.lay_move2);
        LinearLayout lay_center = (LinearLayout) findViewById(R.id.lay_center);
        lay_move1.setOnTouchListener(touchListener);
        lay_move2.setOnTouchListener(touchListener);
        lay_center.setOnTouchListener(touchListener);
    }
    public View.OnTouchListener touchListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.lay_center:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (check_attack == false&&check_move2==true) {//1p어택
                                initX = event.getX();
                                initY = event.getY();
                                iv_ball1.setX(test1.getX()+65);
                                iv_ball1.setY(650);
                                test1.setImageResource(R.drawable.right_throwing);
                            } else if(check_attack == true&&check_move1==true){//2p어택
                                initX = event.getX();
                                initY = event.getY();
                                iv_ball2.setX(test2.getX()+1109);
                                iv_ball2.setY(650);
                                test2.setImageResource(R.drawable.left_throwing);
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            float diffX = event.getX() - initX;
                            float diffY = event.getX() - initX;
                            if (event.getX() <= 1044 && check_attack == false&&check_move2==true) {//1p어택애니메이션
                                Path path1 = new Path();
                                path1.arcTo(iv_ball1.getX(), 780-Math.abs(diffY), Math.abs(diffX*4),
                                        1300, 180f, 180f, true);
                                ObjectAnimator animation1 = ObjectAnimator.ofFloat(iv_ball1, iv_ball1.X,
                                        iv_ball1.Y, path1);
                                animation1.setDuration(1000);
                                animation1.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        iv_ball1.setVisibility(View.VISIBLE);
                                    }
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        if(test2.getX()+894<= iv_ball1.getX()&&iv_ball1.getX()<=test2.getX()+1194) {
                                            test2.setImageResource(R.drawable.hit_right);
                                            ImageView rightlife = (ImageView)findViewById(R.id.rightlife);
                                            player2_life-=1;
                                            if(player2_life==2){
                                                rightlife.setImageResource(R.drawable.life2right);
                                            }else if(player2_life==1){
                                                rightlife.setImageResource(R.drawable.life1right);
                                            }
                                            else if(player2_life==0){
                                                Intent outIntent = new Intent(getApplicationContext(),MainActivity.class);
                                                outIntent.putExtra("activity_result",1);
                                                setResult(RESULT_OK,outIntent);
                                                finish();
                                            }
                                        }
                                        test1.setImageResource(R.drawable.right_move);
                                        iv_ball1.setVisibility(View.GONE);
                                        check_attack = true;
                                        check_move1=false;
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                });
                                animation1.start();
                            } else if (event.getX() > 1044 && check_attack == true&&check_move1==true) {//event.getX()>1044
                                Path path2 = new Path();
                                path2.arcTo(2088-Math.abs(diffX*4), 780-Math.abs(diffY),iv_ball2.getX() , 1300, 0f, -180f, true);
                                ObjectAnimator animation2 = ObjectAnimator.ofFloat(iv_ball2, iv_ball2.X, iv_ball2.Y, path2);
                                animation2.setDuration(1000);
                                animation2.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        iv_ball2.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        if(test1.getX()-150<= iv_ball2.getX()&&iv_ball2.getX()<=test1.getX()+150) {
                                            test1.setImageResource(R.drawable.hit_left);
                                            ImageView leftlife = (ImageView)findViewById(R.id.leftlife);
                                            player1_life-=1;
                                            if(player1_life==2){
                                                leftlife.setImageResource(R.drawable.life2left);
                                            }else if(player1_life==1){
                                                leftlife.setImageResource(R.drawable.life1left);
                                            }
                                            else if(player1_life==0){
                                                Intent outIntent = new Intent(getApplicationContext(),MainActivity.class);
                                                outIntent.putExtra("activity_result",2);
                                                setResult(RESULT_OK,outIntent);
                                                finish();
                                            }
                                        }
                                        test2.setImageResource(R.drawable.left_move);
                                        iv_ball2.setVisibility(View.GONE);
                                        check_attack = false;
                                        check_move2 = false;
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                });
                                animation2.start();
                            }
                            break;
                    }
                    return true;
                case R.id.lay_move1:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initX = event.getX();
                            x=0;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (check_attack&&!check_move1) {
                                float diffX = event.getX() - initX;
                                if(event.getHistorySize()>0){
                                    x = event.getHistoricalX(0);
                                }else
                                    x=0;
                                if(right1==false&&x>0&&event.getX()-x>0) {
                                    test1.setImageResource(R.drawable.right_move);
                                    right1=true;
                                }
                                else if(right1==true&&x>0&&event.getX()-x<0) {
                                    test1.setImageResource(R.drawable.left_move);
                                    right1=false;
                                }
                                result =result + diffX;
                                if(flag_move==false) {
                                    result = diffX + test1.getTranslationX();
                                    flag_move = true;
                                }
                                test1.setTranslationX(result);
                                result = result-diffX;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            check_move1 = true;
                            flag_move= false;
                            break;
                    }
                    return true;
                case R.id.lay_move2:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initX = event.getX();
                            x=0;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (!check_attack&&!check_move2) {
                                float diffX = event.getX() - initX;
                                if (event.getHistorySize() > 0) {
                                    x = event.getHistoricalX(0);
                                } else
                                    x = 0;
                                if (left2==true&&x > 0 && event.getX() - x > 0){
                                    test2.setImageResource(R.drawable.right_move);
                                    left2=false;
                                }else if(left2==false&&x>0&&event.getX()-x<0) {
                                    test2.setImageResource(R.drawable.left_move);
                                    left2=true;
                                }
                                result =result + diffX;
                                if(flag_move==false) {
                                    result = diffX + test2.getTranslationX();
                                    flag_move = true;
                                }
                                test2.setTranslationX(result);
                                result = result-diffX;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            check_move2 = true;
                            flag_move = false;
                            break;
                    }
                    return true;
                }
            return true;
            }
        };
}