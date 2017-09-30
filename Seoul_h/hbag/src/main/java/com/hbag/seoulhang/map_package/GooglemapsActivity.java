package com.hbag.seoulhang.map_package;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hbag.seoulhang.appbase_package.BaseActivity;
import com.hbag.seoulhang.home_package.BackPressCloseHandler;
import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GooglemapsActivity extends BaseActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private GoogleApiClient mGoogleApiClient = null;
    private GoogleMap mGoogleMap = null;
    private Marker currentMarker = null;

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    private AppCompatActivity mActivity;
    boolean askPermissionOnceAgain = false;
    boolean mRequestingLocationUpdates = false;
    Location mCurrentLocatiion;
    boolean mMoveMapByUser = true;
    boolean mMoveMapByAPI = true;
    public BackPressCloseHandler backPressCloseHandler;
    private long backKeyPressedTime = 0;

    UserProfileData_singleton profile;
    NetworkClient networkClient;
    Ipm ipm;

    public static final String PATH = "data/data/com.hbag.seoulhang/";
    public static final String DB_NAME = "new_gps.db";
    private List<Integer> quizList;

    LocationRequest locationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize_db(getApplication()); //데이터베이스를 앱을 복사하여 초기화한다.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_googlemaps);

        Log.d(TAG, "onCreate");
        mActivity = this;

        ipm = new Ipm();
        profile = UserProfileData_singleton.getInstance();
        backPressCloseHandler = new BackPressCloseHandler(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onResume() {

        super.onResume();

        if (mGoogleApiClient.isConnected()) {

            Log.d(TAG, "onResume : call startLocationUpdates");
            if (!mRequestingLocationUpdates) startLocationUpdates();
        }


        //앱 정보에서 퍼미션을 허가했는지를 다시 검사해봐야 한다.
        if (askPermissionOnceAgain) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askPermissionOnceAgain = false;

                checkPermissions();
            }
        }
    }


    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        } else {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d(TAG, "startLocationUpdates : call FusedLocationApi.requestLocationUpdates");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            mRequestingLocationUpdates = true;
        }

    }


    private void stopLocationUpdates() {

        Log.d(TAG, "stopLocationUpdates : LocationServices.FusedLocationApi.removeLocationUpdates");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mRequestingLocationUpdates = false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        progressOFF(); //프로그래스 애니메이션 종료
        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();

        final String sid = profile.getId();
        final String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);

        //mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

            @Override
            public boolean onMyLocationButtonClick() {

                Log.d(TAG, "onMyLocationButtonClick : 위치에 따른 카메라 이동 활성화");
                mMoveMapByAPI = true;
                return true;
            }
        });
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                Log.d(TAG, "onMapClick :");
            }
        });

        mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {

            @Override
            public void onCameraMoveStarted(int i) {

                if (mMoveMapByUser && mRequestingLocationUpdates) {

                    Log.d(TAG, "onCameraMove : 위치에 따른 카메라 이동 비활성화");
                    mMoveMapByAPI = false;
                }

                mMoveMapByUser = true;

            }
        });


        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {


            }
        });

        networkClient.quiznum(sid, new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                switch (response.code()) {
                    case 200:
                        quizList = response.body();
                        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH+"databases/"+DB_NAME,null, SQLiteDatabase.OPEN_READONLY);
                        Cursor c = db.query("GPSDATA",  null, null, null, null, null, null);
                        boolean already_have = false;

                        while(c.moveToNext()) {
                            String region_name = c.getString(c.getColumnIndex("station_name"));
                            int quiz_num = Integer.parseInt(c.getString(c.getColumnIndex("region_code")));
                            double latitude = c.getDouble(c.getColumnIndex("lat"));
                            double longitude = c.getDouble(c.getColumnIndex("long"));

                            for (int i= 0 ; i<quizList.size();i++) {      //이미 얻은 타겟이 있다면 리스트에 추가하지 않는다.
                                if(quizList.get(i).equals(quiz_num)){
                                    already_have = true;
                                }
                            }

                            if(!already_have) {
                                MarkerOptions makeroptions = new MarkerOptions();
                                makeroptions.position(new LatLng(latitude, longitude)).title(region_name);
                                mGoogleMap.addMarker(makeroptions);
                            }
                            already_have = false;

                        }
                        break;
                    default:
                        Log.e("Error : ", "데이터베이스를 불러올 수 없습니다.");
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {


        Log.d(TAG, "onLocationChanged : ");

        String markerTitle = getResources().getString(R.string.내위치);
        String markerSnippet = getCurrentAddress(location);

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location, markerTitle, markerSnippet);

        mCurrentLocatiion = location;
    }


    @Override
    protected void onStart() {

        if(mGoogleApiClient != null && !mGoogleApiClient.isConnected()){

            Log.d(TAG, "onStart: mGoogleApiClient connect");
            mGoogleApiClient.connect();
        }

        super.onStart();

    }

    @Override
    protected void onStop() {

        if (mRequestingLocationUpdates) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
            stopLocationUpdates();
        }

        if ( mGoogleApiClient.isConnected()) {

            Log.d(TAG, "onStop : mGoogleApiClient disconnect");
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }


    @Override
    public void onConnected(Bundle connectionHint) {


        if ( !mRequestingLocationUpdates ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

                if (hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {

                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                } else {

                    Log.d(TAG, "onConnected : 퍼미션 가지고 있음");
                    Log.d(TAG, "onConnected : call startLocationUpdates");
                    startLocationUpdates();
                    mGoogleMap.setMyLocationEnabled(true);
                }

            }else{

                Log.d(TAG, "onConnected : call startLocationUpdates");
                startLocationUpdates();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed");
        setDefaultLocation();
    }


    @Override
    public void onConnectionSuspended(int cause) {

        Log.d(TAG, "onConnectionSuspended");
        if (cause == CAUSE_NETWORK_LOST)
            Log.e(TAG, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED)
            Log.e(TAG, "onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");
    }


    public String getCurrentAddress(Location location) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, getResources().getString(R.string.지오코더), Toast.LENGTH_LONG).show();
            return getResources().getString(R.string.지오코더);
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, getResources().getString(R.string.잘못된GPS), Toast.LENGTH_LONG).show();
            return getResources().getString(R.string.잘못된GPS);

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, getResources().getString(R.string.주소미발견), Toast.LENGTH_LONG).show();
            return getResources().getString(R.string.주소미발견);

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0);
        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        mMoveMapByUser = false;


        if (currentMarker != null) currentMarker.remove();


        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        //구글맵의 디폴트 현재 위치는 파란색 동그라미로 표시
        //마커를 원하는 이미지로 변경하여 현재 위치 표시하도록 수정해야함.
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentMarker = mGoogleMap.addMarker(markerOptions);


        if ( mMoveMapByAPI ) {

            Log.d( TAG, "setCurrentLocation :  mGoogleMap moveCamera "
                    + location.getLatitude() + " " + location.getLongitude() ) ;
            // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
            mGoogleMap.moveCamera(cameraUpdate);
        }
    }


    public void setDefaultLocation() {

        mMoveMapByUser = false;


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = getResources().getString(R.string.위치정보불가);
        String markerSnippet = getResources().getString(R.string.GPS활성여부);


        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mGoogleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mGoogleMap.moveCamera(cameraUpdate);

    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        boolean fineLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager
                .PERMISSION_DENIED && fineLocationRationale)
            showDialogForPermission(getResources().getString(R.string.GPS퍼미션));

        else if (hasFineLocationPermission
                == PackageManager.PERMISSION_DENIED && !fineLocationRationale) {
            showDialogForPermissionSetting(getResources().getString(R.string.GPS퍼미션체크박스));
        } else if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {


            Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");


            if ( !mGoogleApiClient.isConnected()) {

                Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (permsRequestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults.length > 0) {

            boolean permissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (permissionAccepted) {


                if (!mGoogleApiClient.isConnected()) {

                    Log.d(TAG, "onRequestPermissionsResult : mGoogleApiClient connect");
                    mGoogleApiClient.connect();
                }



            } else {

                checkPermissions();
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(GooglemapsActivity.this);
        builder.setTitle(getResources().getString(R.string.알림));
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.GPSY), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        });

        builder.setNegativeButton(R.string.GPSN, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }

    private void showDialogForPermissionSetting(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(GooglemapsActivity.this);
        builder.setTitle(getResources().getString(R.string.알림));
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton(getResources().getString(R.string.GPSY), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                askPermissionOnceAgain = true;

                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + mActivity.getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(myAppSettings);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.GPSN), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }


    //GPS 활성화 메소드
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(GooglemapsActivity.this);
        builder.setMessage(getResources().getString(R.string.GPSMessage));
        builder.setCancelable(true);
        builder.setPositiveButton(getResources().getString(R.string.ghkrdls), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.취소), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : 퍼미션 가지고 있음");


                        if (!mGoogleApiClient.isConnected()) {

                            Log.d( TAG, "onActivityResult : mGoogleApiClient connect ");
                            mGoogleApiClient.connect();
                        }
                        return;
                    }
                }

                break;
        }
    }

public void quiznumParse(List<Integer> squizList){

}

public void initialize_db(Context context){
    File folder = new File(PATH+"databases");
    folder.mkdirs();
    File outfile = new File(PATH+"databases/"+DB_NAME);

    if (outfile.length() <= 0) {
        AssetManager assetManager = context.getResources().getAssets();
        try {
            InputStream is = assetManager.open(DB_NAME, AssetManager.ACCESS_BUFFER);
            long filesize = is.available();
            byte[] tempdata = new byte[(int) filesize];
            is.read(tempdata);
            is.close();
            outfile.createNewFile();
            FileOutputStream fo = new FileOutputStream(outfile);
            fo.write(tempdata);
            fo.close();
            Log.d("DB","성공");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("DB","실패");
        }
    }

}

    @Override
    public void onBackPressed() {
        Toast toast = new Toast(getApplicationContext());

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(getApplicationContext(), "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT); //백키 토스트 출력
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            Intent intent = new Intent(GooglemapsActivity.this,Home_Main.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            toast.cancel();
        }
    }


}