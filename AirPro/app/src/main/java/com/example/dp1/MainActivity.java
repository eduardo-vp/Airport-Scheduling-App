package com.example.dp1;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dp1.ui.main.SectionsPagerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    private String finalAddress;
    private boolean enLima;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    private String provider;
    private TabLayout tabs;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enLima = seEncuentraEnLima();
        /*
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),enLima);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        */
    }

    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ){
        Log.d("Reslt","FINAL ADDRESS = " + finalAddress);
        if(enLima){
            Log.d("Result","EN LIMAAAAAAAAAAAA");
        }else{
            Log.d("Result","FUERA DE LIMAAAAA");
        }
        Log.d("PERMISOOOOOOO","PERMISOOOOOOO");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),enLima);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.item1){
            Log.d("RIP","ITEMUNOOOOOOO");
        }
        return super.onOptionsItemSelected(item);
    }

    boolean isInWord(String x, String pat){
        for(int i = 0; i < x.length() - pat.length() + 1; ++i) {
            boolean ans = true;
            for(int j = 0; j < pat.length(); ++j)
                if(x.charAt(i+j) != pat.charAt(j))
                    ans = false;
            if(ans) return true;
        }
        return false;
    }

    boolean checkLima(String x){
        return isInWord(x,"lima") || isInWord(x,"Lima") || isInWord(x,"LIMA");
    }

    private boolean seEncuentraEnLima(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
        try{
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null){
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
            StringBuilder builder = new StringBuilder();
            try {
                List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
                int maxLines = address.get(0).getMaxAddressLineIndex();
                for (int i=0; i<maxLines; i++) {
                    String addressStr = address.get(0).getAddressLine(i);
                    builder.append(addressStr);
                    builder.append(" ");
                }
                finalAddress = builder.toString();
                Log.d("FLAG","Final Address = " + finalAddress);
                if(checkLima(finalAddress))
                    return true;
            } catch (IOException e) {
                // Handle IOException
            } catch (NullPointerException e) {
                // Handle NullPointerException
            }
        }
        }catch(Exception e){

        }
        return false;
    }
}