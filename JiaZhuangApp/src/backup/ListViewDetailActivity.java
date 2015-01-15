package backup;

import com.beta.main.CustomTitleBar;
import com.beta.main.R;
import com.beta.main.R.id;
import com.beta.main.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ListViewDetailActivity extends Activity {

	public ListViewDetailActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// ---------------------------------------------------
		// 自定义Activity标题栏
		CustomTitleBar.getTitleBar(this, "蓝景丽家家装--设计师详情");
		// CustomTitleBar.hideBackBtn();
		// ---------------------------------------------------
		/* 返回按钮 */
		Button titleBackBtn = (Button) this
				.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ListViewDetailActivity.this.finish();
			}
		});
		
		setContentView(R.layout.listview_detail);
//		setContentView(R.layout.view_pager_fragment_designer_detail);
	}

}
