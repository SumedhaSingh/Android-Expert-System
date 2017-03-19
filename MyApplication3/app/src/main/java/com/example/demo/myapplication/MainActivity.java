

/**
 * Created by Sumedha on 6/16/15.
 */
package com.example.demo.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.location.*;
import android.app.Activity;

import android.widget.TabHost;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.widget.CheckBox;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Method;

import android.app.admin.DevicePolicyManager;
import android.widget.RatingBar;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends Activity {
    private CheckBox checkbox;
    private CheckBox checkbox2;
    private boolean aa;
    private boolean ab;
    boolean bluetooth_flag;
    boolean wifi_flag;
    boolean root_flag;
    boolean lock_flag;
    boolean location_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);
        tabhost.setup();
        creatingTab(tabhost);

        // Bluetooth
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        TextView txtView = (TextView) findViewById(R.id.text_id);
        bluetooth(mBluetoothAdapter, txtView);

        //Location
        location();

        //Root finding
        rootFinding();

        //build manufacturer
        buildManufacturer();

        //Wifi encryption
        wifiEncryption();

        //Lock screen
        TextView lock = (TextView) findViewById(R.id.lockscreen);
        if (isDeviceSecured()) {
            lock.setText("Lock Screen : lock screen is enabled -- Secure");
        } else {
            lock.setText("Lock Screen : lock screen is disabled -- Risk");
            lock_flag = true;
        }
        addListenerOnChkIos();
        addListenerOnChkIos2();
//////////////////////
        Button clickButton = (Button) findViewById(R.id.button);
        clickButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                final RatingBar ratingbar = (RatingBar) findViewById(R.id.ratingbar);
                ratingbar.setStepSize((float) 0.25);
                ratingbar.setIsIndicator(true);
                ratingbar.setRating((int) (metricCalculator.rating_calculator()));

            }
        });
        //////////////////////////////////////////////////////////////////////////////////////
//        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        List<ScanResult> networkList = wifi.getScanResults();
//        TextView txtView14= (TextView) findViewById(R.id.wifi);
//
//        if (networkList != null) {
//            for (ScanResult network : networkList)
//            {
//                String Capabilities =  network.capabilities;
//                if(Capabilities.contains("WPA"))
//                {
//                    txtView14.setText("WPA Connection");
//                }
//                else if(Capabilities.contains("WEP"))
//                {
//                    txtView14.setText("WEP Connection");
//                }
//                else
//                {
//                    txtView14.setText("Unauthenticated Network");
//                    // Another type of security scheme, open wifi, captive portal, etc..
//                }
////
//            }
//        }

        StringBuilder d = new StringBuilder();
        if (bluetooth_flag) {
            d.append("\n" + "Bluetooth should be disabled");
        }
        if (location_flag) {
            d.append("\n" + "Loaction services should be disabled");
        }
        if (root_flag) {
            d.append("\n" + "Root should be disabled");
        }
        if (wifi_flag) {
            d.append("\n" + "Wifi should be disabled when not used");
        }
        if (lock_flag) {
            d.append("\n" + "Screen Lock should be enabled");
        }
        TextView txtView20 = (TextView) findViewById(R.id.review);
        txtView20.setText(d);
    }

    ///////////////////////////////////////////////////////
    public void addListenerOnChkIos() {

        checkbox = (CheckBox) findViewById(R.id.checkbox);

        checkbox.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(MainActivity.this,
                            "Ok....You use it for development", Toast.LENGTH_LONG).show();
                    if (aa) {
                        Toast.makeText(MainActivity.this, "That is why the device is rooted", Toast.LENGTH_LONG).show();
                        metricCalculator.getroot(4);
                    } else {
                        Toast.makeText(MainActivity.this, "The device is not rooted ....You should root the phone for development", Toast.LENGTH_LONG).show();
                        metricCalculator.getroot(5);
                    }
                } else {
                    if (aa) {
                        Toast.makeText(MainActivity.this, "Warning....Security Risk....Device Rooted", Toast.LENGTH_LONG).show();
                        metricCalculator.getroot(0);
                    } else {
                        Toast.makeText(MainActivity.this, "Device is not rooted", Toast.LENGTH_LONG).show();
                        metricCalculator.getroot(5);
                    }

                }

            }
        });

    }


    public void addListenerOnChkIos2() {

        checkbox = (CheckBox) findViewById(R.id.checkbox2);

        checkbox.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(MainActivity.this,
                            "You should not keep bluetooth on always...poses security risk", Toast.LENGTH_LONG).show();
                    if (ab) {
                        Toast.makeText(MainActivity.this, "Bluetooth in on right now ....you should close it if not being used", Toast.LENGTH_LONG).show();
                        metricCalculator.getBluetooth(1);
                    } else {
                        Toast.makeText(MainActivity.this, "Bluetooth if off right now....it is a good practice", Toast.LENGTH_LONG).show();
                        metricCalculator.getBluetooth(4);
                    }
                } else {
                    if (ab) {
                        Toast.makeText(MainActivity.this, "Warning....Bluetooth is on ", Toast.LENGTH_LONG).show();
                        metricCalculator.getBluetooth(2);
                    } else {
                        Toast.makeText(MainActivity.this, "Good....Switch bluetooth on only when needed ", Toast.LENGTH_LONG).show();
                        metricCalculator.getBluetooth(5);
                    }

                }

            }
        });

    }

    ////////////////////////////////
    public void wifiEncryption() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> networkList = wifi.getScanResults();
        TextView txtView14 = (TextView) findViewById(R.id.wifi);
        if (networkList == null) {
            txtView14.setText(" Wifi: Wifi not supported -- Secure");
            metricCalculator.getwifi(5);
        }
        if (networkList != null) {
            for (ScanResult network : networkList) {
                String Capabilities = network.capabilities;
                if (Capabilities.contains("WPA")) {
                    txtView14.setText("WPA Connection");
                    metricCalculator.getwifi(5);
                } else if (Capabilities.contains("WEP")) {
                    txtView14.setText("WEP Connection");
                    metricCalculator.getwifi(3);
                } else {
                    txtView14.setText("Unauthenticated Network");
                    metricCalculator.getwifi(0);
                    location_flag = true;
                    // Another type of security scheme, open wifi, captive portal, etc..
                }
//
            }
        }
    }

    /////////////////////////
    private boolean isDeviceSecured() {


        String LOCKSCREEN_UTILS = "com.android.internal.widget.LockPatternUtils";
        try {
            Class<?> lockUtilsClass = Class.forName(LOCKSCREEN_UTILS);
            // "this" is a Context, in my case an Activity
            Object lockUtils = lockUtilsClass.getConstructor(Context.class).newInstance(this);

            Method method = lockUtilsClass.getMethod("getActivePasswordQuality");

            int lockProtectionLevel = (Integer) method.invoke(lockUtils); // Thank you esme_louise for the cast hint

            if (lockProtectionLevel >= DevicePolicyManager.PASSWORD_QUALITY_NUMERIC) {
                metricCalculator.getsecurity(5);
                return true;
            } else {
                metricCalculator.getsecurity(2);
                return false;
            }
        } catch (Exception e) {
            Log.e("reflectInternalUtils", "ex:" + e);
        }


        return false;
    }

    //    ////////////////////
    private void buildManufacturer() {
        String info[] = new String[3];
        TextView txtView4 = (TextView) findViewById(R.id.manufacturer);
        String s = Build.MANUFACTURER;
        info[0] = s;
        txtView4.setText("Manufacturer Name : " + s);

//       brand
        TextView txtView9 = (TextView) findViewById(R.id.brand);
        String ik = android.os.Build.BRAND;
        info[2] = ik;
        txtView9.setText("Brand Name : " + ik);
        //build version
        TextView txtView5 = (TextView) findViewById(R.id.build);
        String ss = Build.VERSION.RELEASE;
        txtView5.setText("Build Name : " +ss);
        ///sdk version

//      ///  Model
        String PhoneModel = android.os.Build.MODEL;
        TextView txtView8 = (TextView) findViewById(R.id.model);
        info[1] = PhoneModel;
        txtView8.setText("Phone Model : " +PhoneModel);

//        Device
        String device = Build.DEVICE;
        TextView buildtxt = (TextView) findViewById(R.id.device);
        buildtxt.setText("Device Name : " + device);
//        product
        String prod = Build.PRODUCT;
        TextView prodtxt = (TextView) findViewById(R.id.product);
        prodtxt.setText("Product Name : " + prod);
//        display
//        Build ID
//        Type
        String build_rank;
        InputStream inputStream = getResources().openRawResource(R.raw.absolutefile);
        csvreader csv = new csvreader(inputStream);
        build_rank = csv.rankgetter(info);
//        txtView8.setText(build_rank);

    }

    private void creatingTab(TabHost tabhost) {
        TabHost.TabSpec tabspec = tabhost.newTabSpec("tab");
        tabspec.setContent(R.id.tab1);
        tabspec.setIndicator("User Input");
        tabhost.addTab(tabspec);

        //second tab
        tabspec = tabhost.newTabSpec("tab2");
        tabspec.setContent(R.id.tab2);
        tabspec.setIndicator("User Settings Info");
        tabhost.addTab(tabspec);

        // Third tab

        tabspec = tabhost.newTabSpec("tab3");
        tabspec.setContent(R.id.tab3);
        tabspec.setIndicator("Build Info");
        tabhost.addTab(tabspec);
//        fourth tab
        tabspec = tabhost.newTabSpec("tab4");
        tabspec.setContent(R.id.tab4);
        tabspec.setIndicator("Eval Phone");
        tabhost.addTab(tabspec);
//        fifth tab
        tabspec = tabhost.newTabSpec("tab5");
        tabspec.setContent(R.id.tab5);
        tabspec.setIndicator("Review");
        tabhost.addTab(tabspec);
    }

    private void bluetooth(BluetoothAdapter mBluetoothAdapter, TextView txtView) {
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth

            txtView.setText("Bluetooth: Bluetooth not supported");
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)

                txtView.setText("Bluetooth : Bluetooth switched off -- Secure");
                metricCalculator.getBluetooth(5);
            } else {
                txtView.setText("Bluetooth : Bluetooth switched on -- Risk");
                bluetooth_flag = true;
                metricCalculator.getBluetooth(0);

            }
        }
    }

    private void location() {
        TextView txtView1 = (TextView) findViewById(R.id.text_id1);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //All location services are disabled

            txtView1.setText("Location Services : Location services are off -- Secure");
            metricCalculator.getLocation(5);
        } else {
            txtView1.setText("Location Services : Location services are on  -- Risk");
            metricCalculator.getLocation(1);
            location_flag = true;

        }
    }

    private void rootFinding() {
        TextView txtView2 = (TextView) findViewById(R.id.text_id2);
        boolean a = rootFind.findBinary("su");
        aa = a;
        if (!a)

        {

            txtView2.setText("Root Access : Device not is rooted -- Secure");
            metricCalculator.getroot(5);
        } else

        {

            txtView2.setText("Root Access : Device is rooted -- Risk");
            root_flag = true;
            metricCalculator.getroot(0);
        }
    }

}



