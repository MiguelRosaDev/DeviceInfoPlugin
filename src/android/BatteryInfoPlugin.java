package com.cordova.plugin.BatteryInfoPlugin;

import android.os.BatteryManager;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class BatteryInfoPlugin extends CordovaPlugin {
  
  private static final String TAG = "DeviceInfoPlugin";

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      if (action.equals("getCapacity")) {
      	this.getCapacity(callbackContext);
      	return true;   
      
      } else if (action.equals("getStatus")) {
        this.getStatus(callbackContext);	    
        return true;  

      } else {
        callbackContext.error("\"" + action + "\" is not a recognized action.");
        return false;

        }
    }
		    
  private void getCapacity(final CallbackContext callbackContext) {      
      cordova.getThreadPool().execute(new Runnable() {
        public void run() {
          try {	
          Log.d(TAG, "getCapacity");
		  
	  IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	  Intent batteryStatus = cordova.getActivity().getApplicationContext().registerReceiver(null, ifilter);
	  int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
	  int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
	  float batteryPct = level / (float) scale;
		  
	  callbackContext.success(Math.round(batteryPct * 100));
		  
          } catch (Exception e) {
            callbackContext.error(e.getMessage());
          }
     	}
    	});
  	}
	
   private void getStatus(final CallbackContext callbackContext) {      
      cordova.getThreadPool().execute(new Runnable() {
        public void run() {
          try {	
          Log.d(TAG, "getStatus");
	
		  
	  callbackContext.success("Yes");
		  
          } catch (Exception e) {
            callbackContext.error(e.getMessage());
          }
     	}
    	});
  	}
	
  }
