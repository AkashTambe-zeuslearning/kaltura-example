package com.example.reactnativekalturaplayer;

import com.facebook.react.bridge.Callback;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kaltura.playkit.PKLog;
import com.kaltura.playkit.PKMediaEntry;
import com.kaltura.playkit.PKMediaSource;
import com.reactnativenavigation.NavigationActivity;
import com.kaltura.tvplayer.OfflineManager;
import com.kaltura.tvplayer.offline.OfflineManagerSettings;

import java.io.IOException;
import java.util.List;

public class MainActivity extends NavigationActivity {
    private PKLog log = PKLog.get("MainActivity");
    public static OfflineManager commonManager = null;
    public static Item commonItem = null;
    public static Callback commonCallback = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OfflineManager manager = OfflineManager.getInstance(this, OfflineManager.OfflineProvider.DTG);
        manager.setOfflineManagerSettings(new OfflineManagerSettings().setHlsAudioBitrateEstimation(OfflineManagerSettings.DEFAULT_HLS_AUDIO_BITRATE_ESTIMATION));

        manager.setAssetStateListener(new OfflineManager.AssetStateListener() {
                @Override
                public void onAssetDownloadFailed(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType, @NonNull Exception error) {
                    updateItemAsset(assetId);
                    if(commonCallback != null){
                       InvokeCallback("onAssetDownloadFailed","error occured while asset download");
                    }
                    log.d("KOM : video download failed");
                }

                @Override
                public void onAssetDownloadComplete(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType) {
                    updateItemAsset(assetId);
                     if(commonCallback != null){
                         InvokeCallback(null, "Asset Download Complete");
                     }
                    log.d("KOM : video download finished");
                    log.d("KOM : " + Uri.parse(commonItem.getAssetInfo().getAssetId()));
                    try {
                        PKMediaEntry entry = manager.getLocalPlaybackEntry(String.valueOf(Uri.parse(commonItem.getAssetInfo().getAssetId())));
                        log.d("KOM : entry local id = " + entry.getId());
                        log.d("KOM : entry local name = " + entry.getName());
                        log.d("KOM : entry local name = " + entry.getName());
                        log.d("KOM : entry local media type = " + entry.getMediaType());
                        List<PKMediaSource> entrySourceList = entry.getSources();
                        log.d("KOM : entry local url = " + entrySourceList.get(0).getUrl());
                        log.d("KOM : entry local media format = " + entrySourceList.get(0).getMediaFormat());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAssetPrefetchComplete(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType) {
                    // if(commonCallback != null){
                    //     //
                    // }
                }

                @Override
                public void onAssetDownloadPending(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType) {
                    // if(commonCallback != null){
                    //     //
                    // }
                }

                @Override
                public void onAssetDownloadPaused(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType) {
                    // if(commonCallback != null){
                    //     //
                    // }
                }

                @Override
                public void onRegistered(@NonNull String assetId, @NonNull OfflineManager.DrmStatus drmStatus) {
                    // if(commonCallback != null){
                    //     //
                    // }
                }

                @Override
                public void onRegisterError(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType, @NonNull Exception error) {
                    // if(commonCallback != null){
                    //     InvokeCallback("onRegisterError","error occured while register");
                    // }
                }

                @Override
                public void onUnRegisterError(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType, @NonNull Exception error) {
                    // if(commonCallback != null){
                    //     InvokeCallback("onUnRegisterError","error occured while unregister");
                    // }
                    //
                }

                @Override
                public void onStateChanged(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType, @NonNull OfflineManager.AssetInfo assetInfo) {
                    // if(commonCallback != null){
                    //     //
                    // }
                }

                @Override
                public void onAssetRemoved(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType) {
                    // if(commonCallback != null){
                    //     InvokeCallback(null, "Asset Removed successfully");
                    // }
                }

                @Override
                public void onAssetRemoveError(@NonNull String assetId, @NonNull OfflineManager.DownloadType downloadType, @NonNull Exception error) {
                    // if(commonCallback != null){
                    //     InvokeCallback("onAssetRemoveError","error occured while asset remove");;
                    // }
                }
            });

        manager.setDownloadProgressListener((assetId, bytesDownloaded, totalBytesEstimated, percentDownloaded) -> {
            // if(commonCallback != null && percentDownloaded == 100){
            //     InvokeCallback(null, "Asset Downloaded Finished");
            // }
            log.d("KOM : percentage downloaded " + percentDownloaded);
        });

        try {
            manager.start(() -> {
                log.d("KOM : manager started");
                setCommonManager(manager);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter
    public static Item getCommonItem() {
        return commonItem;
    }

    // Getter
    public static OfflineManager getCommonManager() {
        return commonManager;
    }

    // Setter
    public static void setCommonManager(OfflineManager manager) {
        commonManager = manager;
    }

    // Setter
    public static void setCommonCallback(Callback callback) {
        commonCallback = callback;
    }

    // Setter 
    public static void setCommonItem(Item item) {
        commonItem =  item;
    }

    public static void updateItemAsset(String assetId){
        if(assetId == commonItem.id()){
            commonItem.setAssetInfo(commonManager.getAssetInfo(commonItem.id()));
        }
    }

    public static void setAssetInfo(OfflineManager.AssetInfo assetInfo){
        if(commonItem != null){
            commonItem.setAssetInfo(assetInfo);
        }
    }

    public static void InvokeCallback(String err, String msg){
        if(commonCallback != null){
            commonCallback.invoke(err,msg);   
        }
    }
}
