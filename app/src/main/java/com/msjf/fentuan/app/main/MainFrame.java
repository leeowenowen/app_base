package com.msjf.fentuan.app.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.location.core.AMapLocException;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.AMap.OnMapLongClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.AMap.OnMarkerDragListener;
import com.amap.api.maps2d.AMap.OnMyLocationChangeListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.LocationSource.OnLocationChangedListener;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.msjf.fentuan.app.common.DownloadState;
import com.msjf.fentuan.app.main.FenTaQiaoTaView.ItemView;
import com.msjf.fentuan.gateway_service.ShuoBaData;
import com.msjf.fentuan.gateway_service.http.JsonResponseHandler;
import com.msjf.fentuan.gateway_service.user.UserClient;
import com.msjf.fentuan.log.Logger;
import com.msjf.fentuan.map.AMapLocationListenerAdapter;
import com.msjf.fentuan.ui.util.TU;
import com.msjf.fentuan.user.MobUser;
import com.msjf.fentuan.user.Self;
import com.msjf.fentuan.user.User;
import com.msjf.fentuan.user.UserData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.owo.app.common.ContextManager;
import com.owo.app.common.DLog;
import com.owo.app.language.LanguageObserver;
import com.owo.app.theme.BitmapId;
import com.owo.app.theme.Theme;
import com.owo.app.theme.ThemeObserver;
import com.owo.base.common.Callback;
import com.owo.base.pattern.Singleton;
import com.owo.base.util.BitmapHelper;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.ConfigurableDialog;
import com.owo.ui.utils.LP;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainFrame extends LinearLayout implements ThemeObserver, LanguageObserver {

    private static final String TAG = "MainFrame";
    /**
     * <pre>
     * Layers:
     * 	UserWidetLayer
     *  MapMarkLayer
     * </pre>
     */
    private ClubTitleWidget mClubTitleLayout;
    private ClubContentWidget mClubInfoWidget;
    private MapView mMapView;
    private AMap mMap;
    private FrameLayout mContentContainer;

    private LocationManagerProxy mLocationManagerProxy;

    public MainFrame(Context context) {
        super(context);
        initComponents(context);
        setupListeners();
        onLanguageChanged();
        onThemeChanged();
    }

    private void initComponents(Context context) {
        mClubTitleLayout = new ClubTitleWidget(context);
        mClubInfoWidget = new ClubContentWidget(context);
        mMapView = new MapView(context);
        mMap = mMapView.getMap();

        mContentContainer = new FrameLayout(context);
        mContentContainer.addView(mMapView, LP.FMM);
        mContentContainer.addView(mClubInfoWidget, LP.FMM);

        setOrientation(LinearLayout.VERTICAL);
        addView(mClubTitleLayout, LP.LMW());
        addView(mContentContainer, LP.LM01);
        //setBackgroundColor(Color.GREEN);
    }

    private class OnLocationChangedListenerWrapper implements OnLocationChangedListener {
        private boolean mIsActive;
        private OnLocationChangedListener mInner;

        public void updateInner(OnLocationChangedListener inner) {
            mInner = inner;
        }

        public void activate() {
            mIsActive = true;
        }

        public void deactivate() {
            mIsActive = false;
        }

        @Override
        public void onLocationChanged(Location location) {
            if (mIsActive && mInner != null) {
                mInner.onLocationChanged(location);
            }
        }

    }

    private MotionEvent mLastEv;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mLastEv = ev;
        return super.onInterceptTouchEvent(ev);
    }

    private void setupListeners() {
        mOnLocationChangedListener = new OnLocationChangedListenerWrapper();

        LocationSource locationSource = new LocationSource() {
            @Override
            public void activate(final OnLocationChangedListener listener) {
                DLog.v(TAG, "MainFrame:activate");
                mOnLocationChangedListener.updateInner(listener);
            }

            @Override
            public void deactivate() {
                DLog.v(TAG, "MainFrame:deactivate");
                mOnLocationChangedListener.updateInner(null);
            }
        };
        mMap.setLocationSource(locationSource);
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setOnCameraChangeListener(new OnCameraChangeListener() {

            @Override
            public void onCameraChangeFinish(CameraPosition arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onCameraChange(CameraPosition arg0) {
                // TODO Auto-generated method stub

            }
        });

        mMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub

            }
        });

        mMap.setOnMapLoadedListener(new OnMapLoadedListener() {

            @Override
            public void onMapLoaded() {
                // TODO Auto-generated method stub

            }
        });

        mMap.setOnMapLongClickListener(new OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng arg0) {
                // TODO Auto-generated method stub

            }
        });

        mMap.setOnMarkerDragListener(new OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub

            }
        });

        mMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location arg0) {
                // TODO Auto-generated method stub

            }
        });
        mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker mark) {
                MarkInfoView view = new MarkInfoView(getContext());
                view.setText(mark.getTitle());
                return view;
            }

            @Override
            public View getInfoContents(Marker mark) {
                // TODO Auto-generated method stub
                return null;
            }
        });

        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker == mMarker) {
                    return true;
                }
                final User user = (User) marker.getObject();
                final ConfigurableDialog dialog = new ConfigurableDialog(getContext(), false);
                FenTaQiaoTaView view = new FenTaQiaoTaView(getContext(), user);
                view.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                if (user.isFocused()) {
                                    UserClient.cancel_focus_user(Self.user().getId(), user.getId(), new JsonResponseHandler() {
                                        @Override
                                        public void onSuccess(JSONObject jsonObject) {
                                            user.setFocusFlag(1);
                                        }

                                        @Override
                                        public void onFailure(JSONObject jsonObject) {
                                            Toast.makeText(getContext(), " 取消关注失败 ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    UserClient.focus_user(Self.user().getId(), user.getId(), new JsonResponseHandler() {
                                        @Override
                                        public void onSuccess(JSONObject jsonObject) {
                                            user.setFocusFlag(0);
                                        }

                                        @Override
                                        public void onFailure(JSONObject jsonObject) {
                                            Toast.makeText(getContext(), " 关注失败 ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                break;
                            case 1:
                                int offset = 20;
                                TranslateAnimation leftTranslateAnimation = new TranslateAnimation(0,
                                        offset, 0, offset);
                                CycleInterpolator interpolator = new CycleInterpolator(4);
                                leftTranslateAnimation.setInterpolator(interpolator);
                                leftTranslateAnimation.setDuration(300);
                                MainFrame.this.startAnimation(leftTranslateAnimation);
                                break;
                            default:
                                break;
                        }
                    }
                });
                // setContentView first and setAttributes later
                dialog.setContentView(view);
                WindowManager.LayoutParams param = dialog.getWindow().getAttributes();
                param.width = DimensionUtil.w(250);
                param.height = WindowManager.LayoutParams.WRAP_CONTENT;
                param.dimAmount = 0;
                param.x = (int) mLastEv.getX();
                param.y = (int) mLastEv.getY();
                param.gravity = Gravity.LEFT | Gravity.TOP;
                dialog.getWindow().setAttributes(param);
                dialog.show();

                // final ConfigurablePopupWindow popupWindow = new
                // ConfigurablePopupWindow();
                // popupWindow.setContentView(view);
                // popupWindow.setWidth(DimensionUtil.w(200));
                // popupWindow.setHeight(DimensionUtil.h(200));
                // popupWindow.setBackgroundDrawable(new
                // ColorDrawable(Color.RED));
                //
                // popupWindow.showAtLocation(MainFrame.this,
                // Gravity.NO_GRAVITY,
                // (int) mLastEv.getX(), (int) mLastEv.getY());

                return true;
            }

            ;
        });
        final ShuoBaData instance = ShuoBaData.instance();
        instance.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        mInvalidCount++;
                        final int invalidCount = mInvalidCount;
                        //clear old marks
                        for (Marker marker : mMarkers) {
                            marker.remove();
                            marker.destroy();
                        }
                        mMarkers.clear();
                        //add new marks
                        for (final ShuoBaData.ShuoBaItem item : instance.getData()) {
                            User user = UserData.getUser(item.getUserId());
                            if (user == null) {
                                user = new User();
                                user.setId(item.getUserId());
                                user.setDownloadState(DownloadState.Downloading);
                                UserData.addUser(user);
                            }
                            final Marker marker = addUserMark(user, item);

                            if (user.getDownloadState() == DownloadState.Downloaded) {
                                if (user.getMapX() != null && user.getMapY() != null) {
                                    marker.setPosition(new LatLng(item.getLongitude(), item.getLatitude()));
                                }
                                if (user.getPhoto().getDownloadState() == DownloadState.Downloaded) {
                                    setMarkBmp(marker, user.getPhoto().getBmp());
                                }
                            } else {
                                setMarkBmp(marker, Singleton.of(Theme.class).bitmap(BitmapId.me_default_photo));
                                final User destUser = user;
                                UserClient.get_user_info(item.getUserId(), item.getUserId(), new JsonResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) {
                                        Logger.v(TAG, "get_user_info success !" + jsonObject.toJSONString());
                                        JSONObject jsonUser = jsonObject.getJSONObject("userInfo");
                                        JSONObject jsonMobUser = jsonObject.getJSONObject("easemob_user");
                                        MobUser mobUser = MobUser.fromJson(jsonMobUser);
                                        destUser.setMobUser(mobUser);
                                        User.parserJson(destUser, jsonUser);
                                        destUser.setDownloadState(DownloadState.Downloaded);
                                        if (destUser.getMapX() != null && destUser.getMapY() != null) {
                                            marker.setPosition(new LatLng(destUser.getMapX().doubleValue(), destUser.getMapY().doubleValue()));
                                        }
                                        String url = destUser.getAvatarThumbnail();
                                        if (url != null) {
                                            try {
                                                url = URLDecoder.decode(url, "UTF-8");
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                            ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
                                                @Override
                                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                                    destUser.getPhoto().setBmp(loadedImage);
                                                    destUser.getPhoto().setDownloadState(DownloadState.Downloaded);
                                                    Bitmap bg = Singleton.of(Theme.class).bitmap(BitmapId.main_location);
                                                    setMarkBmp(marker, loadedImage);
                                                    Logger.v(TAG, "下载到图片[id:" + destUser.getId() + "]");
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onFailure(JSONObject jsonObject) {
                                        Logger.v(TAG, "get_user_info Failed !");
                                        destUser.setDownloadState(DownloadState.None);
                                    }
                                });
                            }


                        }
                    }
                });
            }
        });

        mClubInfoWidget.setShuoBaSendCallback(new Callback<String>() {
            @Override
            public void run(String param) {
                mMarker.setTitle(param + "\n");
                try {
                    mMarker.showInfoWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void setMarkBmp(Marker marker, Bitmap bmp) {
        Bitmap bg = Singleton.of(Theme.class).bitmap(BitmapId.main_location);
        Bitmap fg = BitmapHelper.createScaledBitmap(bmp, DimensionUtil.w(34), DimensionUtil.w(34));
        BitmapCompositeUtil.composite(bg, fg, DimensionUtil.w(8), DimensionUtil.w(5));
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(fg));
    }


    private int mInvalidCount = 0;

    private Marker addUserMark(User user, ShuoBaData.ShuoBaItem data) {
        Bitmap bg = Singleton.of(Theme.class).bitmap(BitmapId.main_location);
        Bitmap fg = Singleton.of(Theme.class).bitmap(BitmapId.common_app_logo);
        fg = BitmapHelper.createScaledBitmap(fg, DimensionUtil.w(34), DimensionUtil.w(34));
        BitmapCompositeUtil.composite(bg, fg, DimensionUtil.w(8), DimensionUtil.w(5));
        Marker marker = mMap.addMarker(new MarkerOptions().title(data.getContent() + "\n   ")
                .anchor(0.5f, 1.0f).icon(BitmapDescriptorFactory.fromBitmap(fg))
                .draggable(true));
        marker.setObject(user);
        mMarkers.add(marker);

        return marker;
    }

    private List<Marker> mMarkers = new ArrayList<Marker>();
    private Marker mMarker;


    private OnLocationChangedListenerWrapper mOnLocationChangedListener;
    private AMapLocationListener mAMapLocationListener = new AMapLocationListenerAdapter() {


        @Override
        public void onLocationChanged(AMapLocation location) {
            AMapLocException exception = location.getAMapException();
            if (exception != null && exception.getErrorCode() != 0) {
                DLog.e(TAG, "onLocationChanged[error_code:%d][msg:%s]", exception.getErrorCode(),
                        exception.getMessage());
                return;
            }

            // my position
            if (mMarker == null) {
                Bitmap bg = Singleton.of(Theme.class).bitmap(BitmapId.main_location);
                Bitmap fg = Self.user().getPhoto().getBmp();
                if (fg != null) {
                    fg = BitmapHelper.createScaledBitmap(fg, DimensionUtil.w(34), DimensionUtil.w(34));
                    BitmapCompositeUtil.composite(bg, fg, DimensionUtil.w(8), DimensionUtil.w(5));
                    mMarker = mMap.addMarker(new MarkerOptions().anchor(0.5f, 1.0f).icon(BitmapDescriptorFactory.fromBitmap(bg))
                            .draggable(true));
                    mMarker.setObject(Self.user());
                }
            }
            mMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
            Self.user().setMapX(new BigDecimal(location.getLatitude()));
            Self.user().setMapY(new BigDecimal(location.getLongitude()));
            UserClient.geo_update("" + location.getLatitude(), "" + location.getLongitude());
//             mMarker.setSnippet("GPS坐标:［latitude:" + location.getLatitude() +
//             "］[longitude:"
//             + location.getLongitude() + "]");
            try {
                //mMarker.showInfoWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // test_end

            mOnLocationChangedListener.onLocationChanged(location);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        mMapView.onDestroy();
    }

    @SuppressWarnings("deprecation")
    public void onPause() {
        mMapView.onPause();
        mOnLocationChangedListener.deactivate();

        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(mAMapLocationListener);
            mLocationManagerProxy.destory();
            mLocationManagerProxy = null;
        }
        mHandler.removeCallbacks(mRefreshMarkRunnable);
    }

    private static Handler mHandler = new Handler();

    private Runnable mRefreshMarkRunnable = new Runnable() {
        @Override
        public void run() {
            UserClient.get_nearby_user_msg(null);
            mHandler.removeCallbacks(mRefreshMarkRunnable);
            mHandler.postDelayed(this, 30 * 1000);
        }
    };

    public void onResume() {
        mHandler.postDelayed(mRefreshMarkRunnable, 30 * 1000);

        mMapView.onResume();
        mOnLocationChangedListener.activate();

        if (mLocationManagerProxy == null) {
            mLocationManagerProxy = LocationManagerProxy.getInstance(ContextManager.activity());
            /*
             * mAMapLocManager.setGpsEnable(false);//
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
			 */
            // Location API定位采用GPS和网络混合定位方式，时间最短是2000毫秒
            mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 15000, 10,
                    mAMapLocationListener);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onLanguageChanged() {
    }

    @Override
    public void onThemeChanged() {
    }
}
