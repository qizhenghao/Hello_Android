package com.bruce.android.knowledge.utils.phone;

import android.content.Context;

import com.bruce.android.knowledge.utils.phone.scheme.CallSchemeAcceptADB;
import com.bruce.android.knowledge.utils.phone.scheme.CallSchemeAcceptAPI19;
import com.bruce.android.knowledge.utils.phone.scheme.CallSchemeAcceptAPI26;
import com.bruce.android.knowledge.utils.phone.scheme.CallSchemeAcceptAPI26_23;
import com.bruce.android.knowledge.utils.phone.scheme.CallSchemeReject;
import com.bruce.android.knowledge.utils.phone.scheme.ICallSchemeAccept;
import com.bruce.android.knowledge.utils.phone.scheme.ICallSchemeReject;


/**
 * created by Lin on 2017/12/16
 */

public class CallHelper {
    
    private static CallHelper sInstance = null;
    public synchronized static CallHelper getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CallHelper(context.getApplicationContext());
        }
        return sInstance;
    }
    
    private ICallSchemeAccept mICallSchemeAccept;
    private ICallSchemeReject mICallSchemeReject;
    private CallHelper(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            if (context.getApplicationInfo().targetSdkVersion <= 22) {
                mICallSchemeAccept = new CallSchemeAcceptAPI26();
            } else {
                mICallSchemeAccept = new CallSchemeAcceptAPI26_23();
            }
        } else if (android.os.Build.VERSION.SDK_INT >= 19) {
            mICallSchemeAccept = new CallSchemeAcceptAPI19();
        } else {
            mICallSchemeAccept = new CallSchemeAcceptADB();
        }
        mICallSchemeReject = new CallSchemeReject();
    }
    
    public void rejectCall(Context context) throws Exception {
        mICallSchemeReject.rejectCall(context);   
    }
    
    public void acceptCall(Context context) throws Exception {
        mICallSchemeAccept.acceptCall(context);
    }
    
}
