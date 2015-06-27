package com.msjf.fentuan.map;

import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocationListener;

public abstract class AMapLocationListenerAdapter implements AMapLocationListener {

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
}
