package com.main.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TextFragment extends Fragment{  
    
    private String mMsg;  
      
    public void setMessage(String message){  
        this.mMsg = message;  
    }  
      
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        final Context context = getActivity();  
        FrameLayout root = new FrameLayout(context);  
        root.setBackgroundColor(Color.YELLOW);  
        TextView tv = new TextView(context);  
        tv.setText(mMsg);  
        tv.setGravity(Gravity.CENTER);  
        root.addView(tv, new FrameLayout.LayoutParams(  
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));  
        return root;  
    }  
  
}  
