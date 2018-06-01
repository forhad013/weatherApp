package com.example.forhad.weatherapp.activity.splash_activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.forhad.weatherapp.R;

import com.example.forhad.weatherapp.activity.wetherlist_activity.WeatherListActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SplashActivity extends AppCompatActivity implements SplashActivityMVP.View{



    SplashActivityMVP.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenterImpl(this);

        SplashActivityPermissionsDispatcher.runSplashWithPermissionCheck(SplashActivity.this);

    }


    @NeedsPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
    public  void runSplash(){
        presenter.runTimer();
    }
    @OnShowRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
    void showRationaleForLocation(final PermissionRequest request) {
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(R.string.location_message)
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();

                    }
                })
                .show();
    }

    @OnPermissionDenied(android.Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForLocation() {
        Toast.makeText(getApplicationContext(), "Permission dined", Toast.LENGTH_SHORT).show();
        finish();
    }

    @OnNeverAskAgain(android.Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForLocation() {
        Toast.makeText(getApplicationContext(), "never ask", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void goToNextScreen() {

        startActivity(new Intent(this,
                WeatherListActivity.class));
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();

    }
}
