package com.example.reactnativekalturaplayer;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.kaltura.tvplayer.KalturaPlayer;
import com.kaltura.tvplayer.OfflineManager;
import com.kaltura.tvplayer.offline.OfflineManagerSettings;
import com.kaltura.playkit.PKMediaEntry;
import com.kaltura.playkit.PKMediaSource;
import com.kaltura.playkit.PKDrmParams;
import com.kaltura.playkit.PKLog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.app.Activity;

public class OfflineKalturaManager extends ReactContextBaseJavaModule {
    private PKLog log = PKLog.get("MainActivity");
    OfflineKalturaManager(ReactApplicationContext context) {   
        super(context);
    }

    @Override 
    public String getName() {
        return "OfflineKalturaManager";
    }

    @ReactMethod
    public void downloadVideo(String title, String playerType, int partnerId, String id, String env, Callback callBack) {
        MainActivity.setCommonCallback(callBack);
        prepareVideo(title, playerType, partnerId, id, env);
    }

    private void prepareVideo(String title, String playerType, int partnerId, String id, String env) {
        OfflineManager manager = MainActivity.getCommonManager();
        if(manager == null){
            log.d("KOM : manager is null");
            MainActivity.InvokeCallback("null", "manager is null");
            return;
        }
        log.d("KOM : manager is not null");
        OfflineManager.SelectionPrefs defaultPrefs = new OfflineManager.SelectionPrefs();
        defaultPrefs.videoBitrate = 600000;
        defaultPrefs.allAudioLanguages = true;
        defaultPrefs.allTextLanguages = true;
        defaultPrefs.allowInefficientCodecs = false;

        OfflineManager.PrepareCallback prepareCallback = new OfflineManager.PrepareCallback() {
            @Override
            public void onPrepared(@NonNull String assetId, @NonNull OfflineManager.AssetInfo assetInfo, @Nullable Map<OfflineManager.TrackType, List<OfflineManager.Track>> selected) {
                log.d("KOM : video is prepared");
                MainActivity.setAssetInfo(assetInfo);
                startVideo();
            }

            @Override
            public void onPrepareError(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType, @NonNull Exception error) {
                log.d("KOM : error while preparing video");
                MainActivity.InvokeCallback("onPrepareError", "error occured while video preparation");
            }

            @Override
            public void onMediaEntryLoadError(@NonNull OfflineManager.DownloadType downloadType, @NonNull Exception error) {
                log.d("KOM : error in media load");
                log.d("KOM : " + error.getMessage());
                MainActivity.InvokeCallback("onMediaEntryLoadError", "error occured while loading media details");
            }

            @Override
            public void onMediaEntryLoaded(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType, @NonNull PKMediaEntry mediaEntry) {
                log.d("KOM : media loaded");
            }

            @Override
            public void onSourceSelected(@NonNull String assetId, @NonNull PKMediaSource source, @Nullable PKDrmParams drmParams) {
                log.d("KOM : source selected");
            }
        };

        Item item = null;
        switch(playerType) {
            case "ott":
                log.d("KOM : item type OTT");
                item = new OTTItem(partnerId, id, env, null, null, null, null, title);
                MainActivity.setCommonItem(item);
                MainActivity.setAssetInfo(manager.getAssetInfo(item.id()));
                manager.setKalturaParams(KalturaPlayer.Type.ott, ((OTTItem) item).getPartnerId());
                manager.setKalturaServerUrl(((OTTItem) item).getServerUrl());
                if(item.getEntry() != null){
                    log.d("KOM : video preparation started");
                    manager.prepareAsset(((KalturaItem) item).mediaOptions(), item.getSelectionPrefs() != null ? item.getSelectionPrefs() : defaultPrefs, prepareCallback);
                }
                else{
                    log.d("KOM : video preparation not started");
                }
                break;
            case "ovp":
                log.d("KOM : item type OVP");
                item = new OVPItem(partnerId, id, env, null, null, title);
                MainActivity.setCommonItem(item);
                MainActivity.setAssetInfo(manager.getAssetInfo(item.id()));
                manager.setKalturaParams(KalturaPlayer.Type.ovp, ((KalturaItem) item).getPartnerId());
                manager.setKalturaServerUrl(((KalturaItem) item).getServerUrl());
                if(((KalturaItem) item).mediaOptions() != null){
                    log.d("KOM : video preparation started");
                    manager.prepareAsset(((KalturaItem) item).mediaOptions(), item.getSelectionPrefs() != null ? item.getSelectionPrefs() : defaultPrefs, prepareCallback);
                }
                else{
                    log.d("KOM : video preparation not started");
                }
                break;
            default:
                break;
        }
    }

    private void startVideo(){
        OfflineManager manager = MainActivity.getCommonManager();
        Item item = MainActivity.getCommonItem();
        if (item.getAssetInfo() == null) {
            log.d("KOM : video download not started");
            MainActivity.InvokeCallback("startVideoFailure", "asset info not found");
        }
        manager.startAssetDownload(item.getAssetInfo());
        MainActivity.updateItemAsset(item.id());
        log.d("KOM : video download started");
    }
}