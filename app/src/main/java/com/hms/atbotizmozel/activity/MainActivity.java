package com.hms.atbotizmozel.activity;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.model.UserModel;
import com.hms.atbotizmozel.fragment.AlphabetFragment;
import com.hms.atbotizmozel.fragment.ReportFragment;
import com.hms.atbotizmozel.util.PrefUtils;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hms.atbotizmozel.activity.ProfileActivity.EXTRA_USER;

public class MainActivity extends AppCompatActivity {


    ImageView back;

    @BindView(R.id.detail)
    TextView detailBtn;
    @BindView(R.id.write)
    TextView writeBtn;
    @BindView(R.id.speak)
    TextView speakBtn;
    @BindView(R.id.spell)
    TextView spellBtn;


    private Thread processingThread;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int MY_PERMISSIONS_REQUEST = 10;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean lock = false;
    int userId;

    private Timer t;
    private AlphabetFragment rf;
    private ReportFragment rff;
    private boolean onalfabe = false;
    private boolean isGetDataEnabled=false;
    private int harf = 0;
    private boolean gosterdim = false;
    private int goster = 0;

    private UserModel userModel;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parent_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_logout:
                PrefUtils.with(MainActivity.this).savePassword("");
                PrefUtils.with(MainActivity.this).saveUserName("");
                PrefUtils.with(MainActivity.this).removeUserModel();
                PrefUtils.with(MainActivity.this).clear();
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userModel = PrefUtils.with(this).getUserModel();
        detailBtn.setEnabled(true);
        reports_on();
        Log.e("ALF", "onCreate");


        speakBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SpeakActivity.class);
                startActivity(intent);
            }



        });

        spellBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SpellActivity.class);
                startActivity(intent);
            }



        });

        writeBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DigitActivity.class);
                startActivity(intent);
            }



        });

        detailBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (goster == 3) {
                    setScreenDefault();
                    goster = 0;
                    detailBtn.setTextColor(getResources().getColor(R.color.white));
                } else if (goster == 0 && harf == 0) {
                    detailBtn.setTextColor(getResources().getColor(R.color.orange));
                    Log.e("ALF","alfabeyi yazıyorum");
                    goster = 3;
                    gosterdim = false;
                    alfabe_on();
                    t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Log.e("GG", String.valueOf(harf) + "gosterdim");
                            harf++;
                            int limit = 39;
                            if (!isTurkish())
                                limit = 36;
                            if (harf > limit)
                                harf = 1;
                            gosterdim = false;
                        }
                    }, 0, 13000);
                }
            }
        });



        processingThread = new Thread() {
            @Override
            public void run() {
                super.run();
                detailBtn.setEnabled(true);
                while (!isFinishing()) {
                    try {
                        handler.sendEmptyMessage(0);
                        handler.sendEmptyMessage(1);
                        if (isGetDataEnabled) {
                            handler.sendEmptyMessage(2);
                        } else {

                        }
                        handler.sendEmptyMessage(3);
                        sleep(30);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        processingThread.start();
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    showAlphabet();
                    break;
            }
        }
    };

    public void alfabe_on() {
        if (!isFinishing()) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            rf = new AlphabetFragment();
            rff = new ReportFragment();
            fragmentTransaction.replace(R.id.layout33, rf);
            fragmentTransaction.commitAllowingStateLoss();
            onalfabe = true;
        }
    }

    public void reports_on() {
        if (!isFinishing()) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            rff = new ReportFragment();
            rf = new AlphabetFragment();
            fragmentTransaction.replace(R.id.layout33, rff);
            fragmentTransaction.commitAllowingStateLoss();
            onalfabe = false;
            rff.updateReport(new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", ""}, new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", ""});
        }
    }


    public void showAlphabet() {
        if (onalfabe && goster == 3 && !gosterdim) {
            try {
                gosterdim = rf.setImage(harf);
                gosterdim = rf.setSound(harf);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isTurkish() {
        String prefix = Locale.getDefault().getLanguage();
        if (prefix.equals("tr"))
            return true;
        else
            return false;
    }

    public void setScreenDefault() {
        if (rf.isVisible()) {
            rf.closeSound();
            t.cancel();
            t.purge();
            rf.setImage(0);
            harf = 0;
            gosterdim = false;


        }
        reports_on();
    }

}
