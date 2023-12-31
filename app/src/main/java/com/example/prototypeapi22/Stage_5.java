package com.example.prototypeapi22;

import androidx.appcompat.app.AppCompatActivity;




import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.view.Gravity;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.view.MotionEvent;
import android.graphics.Matrix;
import android.view.ViewGroup;

import org.w3c.dom.Text;
//ラスボス戦の場所
public class Stage_5 extends AppCompatActivity {
    int tapx = 0;
    int tapy = 0;

    public TextView scoretext;
    public TextView hptext;
    public ImageView hidariue;
    public ImageView ue;
    public ImageView migiue;
    public ImageView migi;
    public ImageView migisita;
    public ImageView sita;
    public ImageView hidarisita;
    public ImageView hidari;
    float bscale = 2; //ボタンの大きさ
    int bx;
    int by;
    float intex;
    float intey;
    int gamestart = 0;
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    float movex = 0;
    float movey = 0;
    //ボタン処理
    int movenum = 0; // 左上→1、上→2、右上3、右4、右下5、下6、左下7、左8、入力なし0
    int tap = 0; // 0 = 押してない、1 = 押している
    int buttontoumeido = 70; //ボタンの透明度、0で完全に透明、255で完全に不透明
    //intだとなぜかsetAlphaに打ち消し線入るけどちゃんと動く
    //floatだと打ち消し線入らないのにちゃんと動かない、謎
    //ボタン処理終わり
    ImageView zimen;
    ImageView hito;
    float hitox = 540;
    float hitoy = 1701;
    int jumptime = 50; //ジャンプの時間
    int jump = -jumptime;
    float jumpx = (float)0.005; //ジャンプの高さを何倍にするか
    ImageView boxn; //通常状態のごみばこ
    ImageView boxr; //右に進んでる時のごみばこ
    ImageView boxl; //左に進んでる時のごみばこ
    int boxscalex = 2; //ごみばこの大きさ倍率
    int boxscaley = 3;

    ImageView mono1;
    ImageView mono2;
    ImageView mono3;
    ImageView mono4;
    ImageView mono5;
    ImageView mono6;
    ImageView mono7;
    ImageView mono8;
    ImageView mono9;
    ImageView mono10;
    ImageView mono11;
    ImageView mono12;

    int obj = 13;
    //float monox[] = new float[obj];
    //float monoy[] = new float[obj];
    float monomovex[] = new float[obj];
    float monomovey[] = new float[obj];
    Random rand = new Random();
    ImageView ball;
    int HP;
    int Score;
    int prevscore;
    int time;
    TextView timetext;
    int cnt;
    TextView load;
    int monox[] = new int[12];
    int monoy[] = new int[12];
    int Clearflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage5);
        //Homeボタンなど消しのおまじない

        //View decorView = getWindow().getDecorView();
//        decorView.setOnSystemUiVisibilityChangeListener
//                (new View.OnSystemUiVisibilityChangeListener() {
//                    @Override
//                    public void onSystemUiVisibilityChange(int visibility) {
//                        // Note that system bars will only be "visible" if none of the
//                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
//                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                            // TODO: The system bars are visible. Make any desired
//                            // adjustments to your UI, such as showing the action bar or
//                            // other navigational controls.
//                            decorView.setSystemUiVisibility(uiOptions);
//                        } else {
//                            // TODO: The system bars are NOT visible. Make any desired
//                            // adjustments to your UI, such as hiding the action bar or
//                            // other navigational controls.
//                            decorView.setSystemUiVisibility(uiOptions);
//                        }
//                    }
//                });
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Intent intent = getIntent();
        HP = intent.getIntExtra("HPgive", 10); //デバッグ用に初期値10
        Score = intent.getIntExtra("Scoregive",0);
        Clearflag = intent.getIntExtra("Clearflag",0);
        prevscore = Score;
        timetext = findViewById(R.id.timetext);

        //ボタン機能
        hidariue = findViewById(R.id.hidariue);
        ue = findViewById(R.id.ue);
        migiue = findViewById(R.id.migiue);
        migi = findViewById(R.id.migi);
        migisita = findViewById(R.id.migisita);
        sita = findViewById(R.id.sita);
        hidarisita = findViewById(R.id.hidarisita);
        hidari = findViewById(R.id.hidari);

        //ボタンのデフォルト設定
        bx = 600; //buttonX
        by = 1500; //buttonY
        intex = 84 * bscale; //boxの大きさ
        intey = 73 * bscale;
        hidariue.setScaleX(bscale);
        hidariue.setScaleY(bscale);
        hidariue.setAlpha(buttontoumeido);
        ue.setScaleX(bscale);
        ue.setScaleY(bscale);
        ue.setAlpha(buttontoumeido);
        migiue.setScaleX(bscale);
        migiue.setScaleY(bscale);
        migiue.setAlpha(buttontoumeido);
        migi.setScaleX(bscale);
        migi.setScaleY(bscale);
        migi.setAlpha(buttontoumeido);
        migisita.setScaleX(bscale);
        migisita.setScaleY(bscale);
        migisita.setAlpha(buttontoumeido);
        sita.setScaleX(bscale);
        sita.setScaleY(bscale);
        sita.setAlpha(buttontoumeido);
        hidarisita.setScaleX(bscale);
        hidarisita.setScaleY(bscale);
        hidarisita.setAlpha(buttontoumeido);
        hidari.setScaleX(bscale);
        hidari.setScaleY(bscale);
        hidari.setAlpha(buttontoumeido);

        //ボタン処理ここまで

        hito = findViewById(R.id.hito);

        scoretext = findViewById(R.id.score);
        hptext = findViewById(R.id.hp);
        scoretext.setX(50);
        scoretext.setY(0);
        hptext.setX(50);
        hptext.setY(100);

        mono1 = findViewById(R.id.mono1);
        mono2 = findViewById(R.id.mono2);
        mono3 = findViewById(R.id.mono3);
        mono4 = findViewById(R.id.mono4);
        mono5 = findViewById(R.id.mono5);
        mono6 = findViewById(R.id.mono6);
        mono7 = findViewById(R.id.mono7);
        mono8 = findViewById(R.id.mono8);
        mono9 = findViewById(R.id.mono9);
        mono10 = findViewById(R.id.mono10);
        mono11 = findViewById(R.id.mono11);
        mono12 = findViewById(R.id.mono12);


        int mlongy = 1; //足場の長さ
        int mlongx = 4;
        mono1.setScaleX(mlongx);
        mono1.setScaleY(mlongy);
        mono2.setScaleX(mlongx);
        mono2.setScaleY(mlongy);
        mono3.setScaleX(mlongx);
        mono3.setScaleY(mlongy);
        mono4.setScaleX(mlongx);
        mono4.setScaleY(mlongy);
        mono5.setScaleX(mlongx);
        mono5.setScaleY(mlongy);
        mono6.setScaleX(mlongx);
        mono6.setScaleY(mlongy);
        mono7.setScaleX(mlongx);
        mono7.setScaleY(mlongy);
        mono8.setScaleX(mlongx);
        mono8.setScaleY(mlongy);
        mono9.setScaleX(mlongx);
        mono9.setScaleY(mlongy);
        mono10.setScaleX(mlongx);
        mono10.setScaleY(mlongy);
        mono11.setScaleX(mlongx);
        mono11.setScaleY(mlongy);
        mono12.setScaleX(mlongx);
        mono12.setScaleY(mlongy);

        //足場の配置決め
        for(int i = 0; i < 12; i++){

            while(true) {
                cnt = 0;
                monox[i] = rand.nextInt(2400) + 1200;
                for(int j = 0; j < 12;j++){
                    if(Math.abs(monox[i] - monox[j]) < 50){
                        cnt += 1;
                    }
                }
                if(cnt == 1){
                    break;
                }
            }
        }

        for(int i = 0; i < 12; i++){

            while(true) {
                cnt = 0;
                monoy[i] = rand.nextInt(1500)+ 400;
                for(int j = 0; j < 12;j++){
                    if(Math.abs(monoy[i] - monoy[j]) < 50){
                        cnt += 1;
                    }
                }
                if(cnt == 1){
                    break;
                }
            }
        }








    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        // Width = 1440, Height = 525
//        decorView.setSystemUiVisibility(uiOptions);
//    }

    public void game(){
        //ボタン処理開始
        float gosax = intex / 4; //なぜか若干誤差が出る(マウスカーソルの当たり判定とタップの判定が違う？)
        float gosay = intey / 4;  //ので調整用,大きさ2倍ならそれぞれinte(x or y)/4
        //当たり判定違う理由わかった、これ座標の基準の点真ん中じゃなくて左上だわ
        if (tap == 1) {
            if (tapx > bx - intex / 2 + gosax && tapx <= bx + intex / 2 + gosax) { //左の列だった場合
                if (tapy > by - intey / 2 + gosay && tapy <= by + intey / 2 + gosay) {
                    movenum = 1;
                } else if (tapy > by + intey / 2 + gosay && tapy <= by + intey * 3 / 2 + gosay) {
                    movenum = 8;
                } else if (tapy > by + intey * 3 / 2 + gosay && tapy < by + intey * 5 / 2 + gosay) {
                    movenum = 7;
                } else {
                    movenum = 0;
                }

            } else if (tapx > bx + intex / 2 + gosax && tapx <= bx + intex * 3 / 2 + gosax) { //真ん中の列だった場合
                if (tapy > by - intey / 2 + gosay && tapy <= by + intey / 2 + gosay) {
                    movenum = 2;
                } else if (tapy > by + intey * 3 / 2 + gosay && tapy < by + intey * 5 / 2 + gosay) {
                    movenum = 6;
                } else {
                    movenum = 0;
                }
            } else if (tapx > bx + intex * 3 / 2 + gosax && tapx <= bx + intex * 5 / 2 + gosax) { //右の列だった場合
                if (tapy > by - intey / 2 + gosay && tapy <= by + intey / 2 + gosay) {
                    movenum = 3;
                } else if (tapy > by + intey / 2 + gosay && tapy <= by + intey * 3 / 2 + gosay) {
                    movenum = 4;
                } else if (tapy > by + intey * 3 / 2 + gosay && tapy < by + intey * 5 / 2 + gosay) {
                    movenum = 5;
                } else {
                    movenum = 0;
                }

            } else {
                movenum = 0;
            }
        } else {
            movenum = 0;
        }
        hidariue.setColorFilter(Color.rgb(0, 0, 0));
        ue.setColorFilter(Color.rgb(0, 0, 0));
        migiue.setColorFilter(Color.rgb(0, 0, 0));
        migi.setColorFilter(Color.rgb(0, 0, 0));
        migisita.setColorFilter(Color.rgb(0, 0, 0));
        sita.setColorFilter(Color.rgb(0, 0, 0));
        hidarisita.setColorFilter(Color.rgb(0, 0, 0));
        hidari.setColorFilter(Color.rgb(0, 0, 0));
        if (movenum == 1) {
            hidariue.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 2) {
            ue.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 3) {
            migiue.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 4) {
            migi.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 5) {
            migisita.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 6) {
            sita.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 7) {
            hidarisita.setColorFilter(Color.rgb(255, 255, 0));
        } else if (movenum == 8) {
            hidari.setColorFilter(Color.rgb(255, 255, 0));
        }
        //ボタン処理終わり

        if (movenum == 8 || movenum == 1 || movenum == 7) {
            if (movex >= -5) {
                movex -= 0.05;
            }
        } else if (movenum == 3 || movenum == 4 || movenum == 5) {
            if (movex <= 5) {
                movex += 0.05;
            }
        } else {
            if (movex < 0) {
                movex += 0.05;
            } else if (movex > 0) {
                movex -= 0.05;
            }
        }
        hitox += movex;
        hito.setScaleY(2);
        hito.setX(hitox);

        hito.setColorFilter(Color.rgb(150, 200, 200));
        if (hitox <= 0) {
            hitox = 0;
            if (movex < 0) {
                movex = 0;
            }
        }
        if (hitox >= 927) {
            hitox = 927;
            if (movex > 0) {
                movex = 0;
            }
        }

        //縦移動処理
        if (movenum == 1 || movenum == 2 || movenum == 3) {
            if (jump == -jumptime) {
                jump = jumptime;
            }
        }
        if (jump > -jumptime) {
            if (movenum == 7 || movenum == 6 || movenum == 5) { //下を押すと早く降りれる
                if (jump > 0) {
                    hitoy -= jump * jump * jumpx;
                } else {
                    hitoy += jump * jump * jumpx;
                }
                jump -= 1;
                if (jump > 0) {
                    hitoy -= jump * jump * jumpx;
                } else {
                    hitoy += jump * jump * jumpx;
                }
                jump -= 1;

            } else {
                if (jump > 0) {
                    hitoy -= jump * jump * jumpx;
                } else {
                    hitoy += jump * jump * jumpx;
                }
                jump -= 1;

            }
            if (jump <= -jumptime) {
                hitoy = 1701;
                jump =  -jumptime;
            }

        }
        hito.setY(hitoy);

        for(int i = 0; i < 12; i++){
            monox[i] -= 1;
        }


        mono1.setX(monox[0]);
        mono2.setX(monox[1]);
        mono3.setX(monox[2]);
        mono4.setX(monox[3]);
        mono5.setX(monox[4]);
        mono6.setX(monox[5]);
        mono7.setX(monox[6]);
        mono8.setX(monox[7]);
        mono9.setX(monox[8]);
        mono10.setX(monox[9]);
        mono11.setX(monox[10]);
        mono12.setX(monox[11]);


        mono1.setY(monoy[0]);
        mono2.setY(monoy[1]);
        mono3.setY(monoy[2]);
        mono4.setY(monoy[3]);
        mono5.setY(monoy[4]);
        mono6.setY(monoy[5]);
        mono7.setY(monoy[6]);
        mono8.setY(monoy[7]);
        mono9.setY(monoy[8]);
        mono10.setY(monoy[9]);
        mono11.setY(monoy[10]);
        mono12.setY(monoy[11]);

        scoretext.setText("" + monox[1]);
        hptext.setText("" + monox[0]);

        if(HP <= 0 || time <= 0){
            Intent intent = new Intent(getApplication(), GameOver.class);
            //Random rand = new Random();
            load.setX(200);
            load.setY(1500);
            load.setScaleX(2);
            load.setScaleY(2);
            load.setText("ロード中...\nしばし待たれよ!");
            timer.cancel();
            intent.putExtra("HPgive",HP);
            intent.putExtra("Scoregive",Score);
            intent.putExtra("MapID",2);
            intent.putExtra("Prevscore",prevscore);
            if(Clearflag % 11 != 0 && HP > 0 && Score >= 10000){
                Clearflag *= 11;
            }
            intent.putExtra("Clearflag",Clearflag);
            startActivity(intent);

        }


    }


    public boolean onTouchEvent (MotionEvent event) {
        //ボタン関係の処理はじめ
        tapx = (int)event.getX();
        tapy = (int)event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            tap = 1;
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
            tap = 0;

        }


        hidariue.setX(bx);
        hidariue.setY(by);
        ue.setX(bx + intex);
        ue.setY(by);
        migiue.setX(bx + intex * 2);
        migiue.setY(by);
        migi.setX(bx + intex * 2);
        migi.setY(by + intey);
        migisita.setX(bx + intex * 2);
        migisita.setY(by + intey * 2);
        sita.setX(bx + intex);
        sita.setY(by + intey * 2);
        hidarisita.setX(bx);
        hidarisita.setY(by + intey * 2);
        hidari.setX(bx);
        hidari.setY(by + intey);
        //ボタン関係の処理終わり
        if(gamestart == 0){ //最初だけgameを起動

            gamestart = 1;
            time = 6000;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            game();
                        }
                    });
                }
            }, 0, 5);
        }

        return true;
    }


}