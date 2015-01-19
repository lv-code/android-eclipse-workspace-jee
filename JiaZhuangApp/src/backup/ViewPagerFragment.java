package backup;

import com.beta.main.R;
import com.beta.main.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerFragment extends Fragment {

	// private String text;
	private int tabNum;

	public ViewPagerFragment(String text, int tabNum) {
		super();
		// this.text = text;
		this.tabNum = tabNum;
	}

	/**
	 * 覆盖此函数，先通过inflater inflate函数得到view最后返回
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		switch (this.tabNum) {
		case 1:
			v = inflater.inflate(R.layout.view_pager_fragment_introduction,
					container, false);
			break;

		case 2:
			v = inflater.inflate(R.layout.view_pager_fragment_designer,
					container, false);
			break;

		default:
			break;
		}
		// tv = (TextView)v.findViewById(R.id.viewPagerText);
		// tv.setText(text);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
//		View target1 = getActivity().findViewById(R.id.textview01);
//		BadgeView badge1 = new BadgeView(getActivity(), target1);
//		badge1.setText("1");
//		badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//		badge1.show(true);
	}
}