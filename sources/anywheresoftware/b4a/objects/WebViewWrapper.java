package anywheresoftware.b4a.objects;

import android.content.Context;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import java.io.InputStream;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@BA.ShortName("WebView")
public class WebViewWrapper extends ViewWrapper<WebView> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // anywheresoftware.b4a.objects.ViewWrapper
    public void innerInitialize(final BA ba, final String str, boolean z) {
        if (!z) {
            setObject(new WebView(ba.context));
            ((WebView) getObject()).getSettings().setJavaScriptEnabled(true);
            ((WebView) getObject()).getSettings().setBuiltInZoomControls(true);
        }
        super.innerInitialize(ba, str, true);
        ((WebView) getObject()).setWebViewClient(new WebViewClient() { // from class: anywheresoftware.b4a.objects.WebViewWrapper.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str2) {
                ba.raiseEvent(WebViewWrapper.this.getObject(), String.valueOf(str) + "_pagefinished", str2);
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str2) {
                Boolean bool = (Boolean) ba.raiseEvent(WebViewWrapper.this.getObject(), String.valueOf(str) + "_overrideurl", str2);
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str2, String str3) {
                Object objRaiseEvent = ba.raiseEvent(WebViewWrapper.this.getObject(), String.valueOf(str) + "_userandpasswordrequired", str2, str3);
                if (objRaiseEvent == null) {
                    httpAuthHandler.cancel();
                } else {
                    String[] strArr = (String[]) objRaiseEvent;
                    httpAuthHandler.proceed(strArr[0], strArr[1]);
                }
            }
        });
        ((WebView) getObject()).setOnTouchListener(new View.OnTouchListener() { // from class: anywheresoftware.b4a.objects.WebViewWrapper.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if ((action != 0 && action != 1) || view.hasFocus()) {
                    return false;
                }
                view.requestFocus();
                return false;
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void LoadUrl(String str) {
        ((WebView) getObject()).loadUrl(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void LoadHtml(String str) {
        ((WebView) getObject()).loadDataWithBaseURL("file:///", str, "text/html", "UTF8", null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void StopLoading() {
        ((WebView) getObject()).stopLoading();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CanvasWrapper.BitmapWrapper CaptureBitmap() {
        Picture pictureCapturePicture = ((WebView) getObject()).capturePicture();
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        bitmapWrapper.InitializeMutable(pictureCapturePicture.getWidth(), pictureCapturePicture.getHeight());
        CanvasWrapper canvasWrapper = new CanvasWrapper();
        canvasWrapper.Initialize2(bitmapWrapper.getObject());
        pictureCapturePicture.draw(canvasWrapper.canvas);
        return bitmapWrapper;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getUrl() {
        return ((WebView) getObject()).getUrl();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean getJavaScriptEnabled() {
        return ((WebView) getObject()).getSettings().getJavaScriptEnabled();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setJavaScriptEnabled(boolean z) {
        ((WebView) getObject()).getSettings().setJavaScriptEnabled(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setZoomEnabled(boolean z) {
        ((WebView) getObject()).getSettings().setBuiltInZoomControls(z);
        if (Build.VERSION.SDK_INT >= 11) {
            ((WebView) getObject()).getSettings().setDisplayZoomControls(z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean getZoomEnabled() {
        return ((WebView) getObject()).getSettings().getBuiltInZoomControls();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean getAllowFileAccess() {
        return ((WebView) getObject()).getSettings().getAllowFileAccess();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAllowFileAccess(boolean z) {
        ((WebView) getObject()).getSettings().setAllowFileAccess(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean Zoom(boolean z) {
        if (z) {
            return ((WebView) getObject()).zoomIn();
        }
        return ((WebView) getObject()).zoomOut();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void Back() {
        ((WebView) getObject()).goBack();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void Forward() {
        ((WebView) getObject()).goForward();
    }

    public static View build(Object obj, HashMap<String, Object> map, boolean z, Object obj2) throws Exception {
        Object obj3 = obj;
        if (obj == null) {
            if (z) {
                Context context = (Context) obj2;
                View view = new View(context);
                InputStream inputStreamOpen = context.getAssets().open("webview.jpg");
                BitmapDrawable bitmapDrawable = new BitmapDrawable(inputStreamOpen);
                inputStreamOpen.close();
                view.setBackgroundDrawable(bitmapDrawable);
                obj3 = view;
            } else {
                WebView webView = (WebView) ViewWrapper.buildNativeView((Context) obj2, WebView.class, map, z);
                webView.getSettings().setJavaScriptEnabled(((Boolean) map.get("javaScriptEnabled")).booleanValue());
                webView.getSettings().setBuiltInZoomControls(((Boolean) map.get("zoomEnabled")).booleanValue());
                obj3 = webView;
                if (Build.VERSION.SDK_INT >= 11) {
                    webView.getSettings().setDisplayZoomControls(((Boolean) map.get("zoomEnabled")).booleanValue());
                    obj3 = webView;
                }
            }
        }
        ViewWrapper.build(obj3, map, z);
        return (View) obj3;
    }
}
