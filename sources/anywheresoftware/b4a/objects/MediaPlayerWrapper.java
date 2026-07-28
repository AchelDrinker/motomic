package anywheresoftware.b4a.objects;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.streams.File;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("MediaPlayer")
public class MediaPlayerWrapper {
    protected String eventName;
    public MediaPlayer mp;

    public void Initialize() throws IllegalStateException, IOException, IllegalArgumentException {
        this.mp = new MediaPlayer();
    }

    public boolean IsInitialized() {
        return this.mp != null;
    }

    public void Initialize2(final BA ba, String str) throws IllegalStateException, IOException, IllegalArgumentException {
        Initialize();
        this.eventName = str.toLowerCase(BA.cul);
        this.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: anywheresoftware.b4a.objects.MediaPlayerWrapper.1
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                ba.raiseEvent(MediaPlayerWrapper.this, String.valueOf(MediaPlayerWrapper.this.eventName) + "_complete", new Object[0]);
            }
        });
    }

    public void Load(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        this.mp.reset();
        loadAfterReset(str, str2);
    }

    private void loadAfterReset(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        if (str.equals(File.getDirAssets())) {
            if (File.virtualAssetsFolder != null) {
                loadAfterReset(File.virtualAssetsFolder, File.getUnpackedVirtualAssetFile(str2));
                return;
            }
            AssetFileDescriptor assetFileDescriptorOpenFd = BA.applicationContext.getAssets().openFd(str2.toLowerCase(BA.cul));
            if (assetFileDescriptorOpenFd.getDeclaredLength() < 0) {
                this.mp.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor());
            } else {
                this.mp.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getDeclaredLength());
            }
        } else if (str.startsWith(File.getDirInternal()) || str.equals(File.getDirInternalCache())) {
            this.mp.setDataSource(new FileInputStream(new java.io.File(str, str2)).getFD());
        } else if (str.equals(File.ContentDir)) {
            AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = BA.applicationContext.getContentResolver().openAssetFileDescriptor(Uri.parse(str2), "r");
            if (assetFileDescriptorOpenAssetFileDescriptor.getDeclaredLength() < 0) {
                this.mp.setDataSource(assetFileDescriptorOpenAssetFileDescriptor.getFileDescriptor());
            } else {
                this.mp.setDataSource(assetFileDescriptorOpenAssetFileDescriptor.getFileDescriptor(), assetFileDescriptorOpenAssetFileDescriptor.getStartOffset(), assetFileDescriptorOpenAssetFileDescriptor.getDeclaredLength());
            }
        } else {
            this.mp.setDataSource(new java.io.File(str, str2).toString());
        }
        this.mp.prepare();
    }

    public boolean getLooping() {
        return this.mp.isLooping();
    }

    public void setLooping(boolean z) {
        this.mp.setLooping(z);
    }

    public void Play() {
        this.mp.start();
    }

    public void Stop() {
        this.mp.reset();
    }

    public void Pause() {
        this.mp.pause();
    }

    public int getDuration() {
        return this.mp.getDuration();
    }

    public int getPosition() {
        return this.mp.getCurrentPosition();
    }

    public void setPosition(int i) {
        this.mp.seekTo(i);
    }

    public void SetVolume(float f, float f2) {
        this.mp.setVolume(f, f2);
    }

    public boolean IsPlaying() {
        return this.mp.isPlaying();
    }

    public void Release() {
        this.mp.release();
    }
}
