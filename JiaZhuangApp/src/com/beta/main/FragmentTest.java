package com.beta.main;

import com.example.jiazhuangapp.R;
import com.readystatesoftware.viewbadger.BadgeView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentTest extends Fragment {  
    private static final String TAG = "TestFragment";  
    private String hello;// = "hello android";  
    private String defaultHello = "default value";  
  //是单例模式吗？
    static FragmentTest newInstance(String s) {  
    	FragmentTest newFragment = new FragmentTest();  
        Bundle bundle = new Bundle();  
        bundle.putString("hello", s);  
        newFragment.setArguments(bundle);  
          
        //bundle还可以在每个标签里传送数据  
        return newFragment;  
  
    }  
  
    
  	public void initViewBadger(View target) {

  		 
  		 BadgeView badge = new BadgeView(getActivity(), target);
  		 badge.setText("99");
  		 badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
  		 badge.setBadgeMargin(0, 0);
  		 badge.show(true);
  	}
  
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {  
        Log.d(TAG, "TestFragment-----onCreateView");  
        Bundle args = getArguments();  
        hello = args != null ? args.getString("hello") : defaultHello;  
        View view = inflater.inflate(R.layout.flow_fragment, container, false);  
        TextView viewhello = (TextView) view.findViewById(R.id.tv);  
        viewhello.setText(hello); 
        View target = view.findViewById(R.id.ImageView0);
        initViewBadger(target);
        return view;  
  
    }  
  
     
  
}  