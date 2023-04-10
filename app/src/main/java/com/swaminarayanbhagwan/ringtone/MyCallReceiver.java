package com.swaminarayanbhagwan.ringtone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyCallReceiver extends BroadcastReceiver {

    private boolean isCallStarted = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Toast.makeText(context, "ACTION_PHONE_STATE_CHANGED", Toast.LENGTH_SHORT).show();
            if (tm.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
                // A call is in progress
                isCallStarted = true;
                Toast.makeText(context, "isCallStarted = true", Toast.LENGTH_SHORT).show();


                // Check if the camera is being used by the phone dialer app
                CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                try {
                    for (String cameraId : cameraManager.getCameraIdList()) {
                        CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
                        Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                        if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                            // Check if the camera is currently being used
//                            Boolean isCameraInUse = characteristics.get(CameraCharacteristics.PHYSICAL_CAMERA_AVAILABILITY) == CameraCharacteristics.PHYSICAL_CAMERA_AVAILABILITY_NOT_AVAILABLE;
//                            if (isCameraInUse) {
                                // The camera is being used by the phone dialer app
                                executeFunction(context);
                                break;
//                            }
                        }
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            } else if (tm.getCallState() == TelephonyManager.CALL_STATE_IDLE && isCallStarted) {
                // The call has ended
                isCallStarted = false;
                Toast.makeText(context, "isCallStarted = false", Toast.LENGTH_SHORT).show();

                executeFunction(context);
            }
            else if (tm.getCallState() == TelephonyManager.CALL_STATE_RINGING){
                Toast.makeText(context, "CALL_STATE_RINGING", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Function to execute if the conditions are met
    private void executeFunction(Context context) {
        // Do something here
        Toast.makeText(context, "executeFunction", Toast.LENGTH_SHORT).show();
    }
}