package com.test.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/*
 * 注：Android4默认情况下是不允许在主线程中访问网络的
 */
public class HttpClientActivity extends Activity {
	private String localhost, baidu, myStr;
	private Button button = null;
	private TextView text = null;
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;
	
	private Button btnTest,btnClear;
	private TextView txtResult;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_http_client);
//		localhost = "http://127.0.0.1/JiaZhuangApp.php";
		localhost = "http://192.168.1.127/JiaZhuangApp.php";
		test();
		AsyncHttp();
	}

	private void init() {
		localhost = "http://127.0.0.1/JiaZhuangApp.php";
		baidu = "http://www.baidu.com";
		// URL url = new URL(localhost);
		InputStream input = null;
		Log.i("break point", "11111");
		input = HttpClientTools.loadFileFromURL(baidu);
		myStr = HttpClientTools.loadTextFromURL(localhost);
		Log.i("msg", myStr + ",  " + input);
		// System.out.println(input);
	}

	//新建线程发送http请求
	private void AsyncHttp() {
		btnTest = (Button) findViewById(R.id.btnTest);  
        btnClear = (Button) findViewById(R.id.btnClear);  
        txtResult = (TextView) findViewById(R.id.txtResult);  
        //  
        handler = new Handler() {  
            public void handleMessage(Message msg) {  
                super.handleMessage(msg);  
                if (msg.what == 0) {  
                    txtResult.append("\nBegin test >>\n");  
                } else if (msg.what == 1) {  
                    txtResult.append(msg.obj.toString());  
                } else if (msg.what == 2) {  
                    txtResult.append("\n<<End test\n");  
                }  
            }  
        };  
        
        txtResult.setText("");  
        txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());  
        btnTest.setOnClickListener(new View.OnClickListener() {  
        	  
            @Override  
            public void onClick(View v) {  
                // doTest();  
                doTestOnAndroid4();  
            }  
        });  
        btnClear.setOnClickListener(new View.OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                doClear();  
  
            }  
        });  
	}
	protected void doClear() {  
        txtResult.setText("");  
  
    }  
  
    protected void doTest() {  
  
        String url = "http://www.baidu.com/"; 
        url = localhost;
        txtResult.append("\nBegin test >>\n");  
        String text = "";  
        try {  
            text = HttpClientTools.loadTextFromURL(url).toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            // text = e.getMessage();  
        }  
        txtResult.append(text);  
        txtResult.append("\n<<End test\n");  
    }  
  
    protected void doTestOnAndroid4() {  
  
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                Message m = new Message();  
                m.what = 0;  
                handler.sendMessage(m);  
                //  
                m = new Message();  
                m.what = 1;  
                String url = "http://www.baidu.com/"; 
                url = localhost;
                try {  
                    m.obj = HttpClientTools.loadTextFromURL(url).toString();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                    // m.obj = e.getMessage();  
                }  
                handler.sendMessage(m);  
                //  
                m = new Message();  
                m.what = 2;  
                handler.sendMessage(m);  
            }  
        }).start();  
  
    }  
	private void test() {
		text = (TextView) findViewById(R.id.text);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 生成一个请求对象，参数就是地址
				HttpGet httpGet = new HttpGet("http://www.baidu.com");
				// 生成Http客户端
				HttpClient httpClient = new DefaultHttpClient();
				InputStream inputStream = null;
				// 使用HTTP客户端发送请求对象
				try {
					// 发送请求的响应
					httpResponse = httpClient.execute(httpGet);
					// 代表接收的http消息，服务器返回的消息都在httpEntity
					httpEntity = httpResponse.getEntity();
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						inputStream = httpEntity.getContent();
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(inputStream));
						String result = "";
						String line = "";
						while ((line = reader.readLine()) != null) {
							result = result + line;
						}

						text.setText(result);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}
}
