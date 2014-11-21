package com.test.main;

import com.example.test.R;

import android.app.Activity;    
import android.content.Intent;    
import android.os.Bundle;    
import android.view.KeyEvent;    
    
public class TransitionNextActivity extends Activity {    
    
    @Override    
    protected void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.activity_transition_next);    
    }    
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {    
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {    
            Intent intent = new Intent(TransitionNextActivity.this, TransitionActivity.class);    
            setResult(RESULT_OK, intent);    
            finish();    
            overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);    
            return false;    
        }    
        return false;    
    }    
    
}    