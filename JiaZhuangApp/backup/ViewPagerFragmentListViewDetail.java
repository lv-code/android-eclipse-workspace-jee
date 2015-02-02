package backup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beta.main.R;
import com.beta.main.R.id;
import com.beta.main.R.layout;
import com.readystatesoftware.viewbadger.BadgeView;

public class ViewPagerFragmentListViewDetail extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.listview_detail, container, false);
		// tv = (TextView)v.findViewById(R.id.viewPagerText);
		// tv.setText(text);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		View target1 = getActivity().findViewById(R.id.textview01);
		BadgeView badge1 = new BadgeView(getActivity(), target1);
		badge1.setText("1");
		badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge1.show(true);
	}

}
