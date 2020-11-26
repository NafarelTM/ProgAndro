package com.example.HelloWorld;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class HomeScreenActivity extends AppCompatActivity {

    private static final String TAG = HomeScreenActivity.class.getSimpleName();

    private Button btnLogout;
    private Button btnStartJob;
    private Button btnStopJob;

    public static final int CAMERA_REQUEST_CODE = 102;
    public  static final int CAMERA_PERMISSION_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;
    private ImageView image;
    private Button btnCamera,galleryBtn;;
    String currentPhotoPath;

    private SharedPrefManager sharedPrefManager;

    private Switch wifiSwitch;
    private WifiManager wifiManager;

    private FrameLayout fragmentHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_activity);

        btnLogout = findViewById(R.id.btnLogout);
        sharedPrefManager = new SharedPrefManager(this);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(sharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(HomeScreenActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        btnStartJob = findViewById(R.id.startJob);
        btnStopJob = findViewById(R.id.stopJob);

        wifiSwitch = findViewById(R.id.wifi_switch);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    wifiManager.setWifiEnabled(true);
                    wifiSwitch.setText("WIFI IS ON ");
                } else{
                    wifiManager.setWifiEnabled(false);
                    wifiSwitch.setText("WIFI IS OFF ");
                }
            }
        });

        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);

        TabLayout tabLayout = findViewById(R.id.tabBar);
        TabItem tabLeft = findViewById(R.id.leftTab);
        TabItem tabRight = findViewById(R.id.rightTab);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition() == 1){
                        pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        image = findViewById(R.id.imageCapture);
        btnCamera = findViewById(R.id.cameraBtn);
        galleryBtn = findViewById(R.id.galleryBtn);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });
    }



    private void onNotifBtnClicked(String message){
        String CHANNEL_ID = "MY_NOTIF_CHANNEL";
        NotificationChannel nChannel = new NotificationChannel(CHANNEL_ID, "My channel", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(nChannel);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Wifi Status")
                .setContentText(message)
                .build();

        int notificationID = 0;
        notificationManager.notify(notificationID, notification);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(wifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiManager.setWifiEnabled(true);
                    wifiSwitch.setChecked(true);
                    wifiSwitch.setText("WIFI IS ON ");
                    onNotifBtnClicked("Wifi On");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiManager.setWifiEnabled(false);
                    wifiSwitch.setChecked(false);
                    wifiSwitch.setText("WIFI IS OFF ");
                    onNotifBtnClicked("Wifi Off");
                    break;
            }
        }
    };

    public void scheduleJob(View view){
        ComponentName componentName = new ComponentName(getApplicationContext(), MyJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.i(TAG, "scheduleJob: Job Scheduled");
        } else{
            Log.i(TAG, "scheduleJob: Job scheduling failed!!");
        }
    }

    public void cancelJob(View view){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.i(TAG, "cancelJob");
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else{
            openCamera();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap imageThumbnail = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(imageThumbnail);
        }
    }
}
