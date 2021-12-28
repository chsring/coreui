package com.srwing.base.launch;

import android.content.Intent;
import android.net.Uri;

/**
 * @author srwing
 * @fileName ActivityResultInfo
 * @time 2018/9/19 上午11:40
 * @desc
 */

public class ActivityResultInfo {
    public int resultCode;
    public Intent data;
    public Uri uri ;

    ActivityResultInfo(int resultCode, Intent data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public Uri getUri() {
        return uri;
    }
    public void setUri(Uri uri) {
        this.uri = uri;
    }


}
