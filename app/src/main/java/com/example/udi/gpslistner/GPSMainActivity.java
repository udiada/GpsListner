package com.example.udi.gpslistner;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/* 1.
      dont forget to add permisions in the manufest:
      <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 */
public class GPSMainActivity extends AppCompatActivity {
    //2.
    TextView tvLatLng,tvAdress;
    LocationManager locationManager;
    Geocoder geocoder;

    //4.
    MyLocLis loclis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsmain);
        tvAdress=(TextView)findViewById(R.id.tvAdress);
        tvLatLng=(TextView)findViewById(R.id.tvLatLng);
    }

    //5.
    @Override
    protected void onResume() {
        super.onResume();

        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        loclis=new MyLocLis();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,20,loclis);  // 3000= 3 miliSEC  //if API 23 andabove you have to add if (see red lamp)
        geocoder=new Geocoder(this);  // convert LatLng to adress
    }

    //6.
    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(loclis);
    }

    //3. built inner class
    private class MyLocLis implements LocationListener
    {

        @Override
        public void onLocationChanged(Location location)
        {
        //7.
            tvLatLng.setText(location.getLatitude()+" , "+location.getLongitude());
            try {
                List<Address> adress=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),3);
                tvAdress.setText("Adress");
                for (Address a:adress)
                {
                    tvAdress.append(a.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
