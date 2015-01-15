

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;


/**
 * @author zg
 * http://square.github.io/retrofit/
 */
public class RetrofitRestTest {
	
	static RequestInterceptor requestInterceptor = new RequestInterceptor() {
		  @Override
		  public void intercept(RequestFacade request) {
		    request.addHeader("Accept", "application/json");
		  }
		};


	public interface JiaZhuangAppService {
		@GET("/JiaZhuangApp.php")
		List<User> listUser();
		
		@GET("/userPotential/getItem?privateId=1&id=1")
		List<User> getItemUser();
		
		//获取多条
		@FormUrlEncoded
		@POST("/userPotential/getList")
		List<User> getListUser(@Field("privateId") String privateId);
		
		//获取多条
		@FormUrlEncoded
		@POST("/userPotential/getList")
		List<User> getListUserWhere(@Field("privateId") String privateId, @Field("data") String data);
		
		//获取多条
		@POST("/userPotential/getList")
		List<User> getListUserWhere2(@Body Data data);
	}
	
	//{"id":1,"name":"Some Guy","email":"example1@example.com","fact":"Loves swimming"}
	public class User {
		String id;
		String name;
		
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]" ;
		}
	}
	
	public static class Data {
//		ArrayList<HashMap<String,String>> data;
		HashMap<String, String> data;
//		String data;
//		String limit;
		String privateId;
		public void data() {
			
		}
	}
	
	private static void test224() {
		RestAdapter restAdapterApi = new RestAdapter.Builder()
	    .setEndpoint("http://192.168.9.224:8001/index.php")
	    .setRequestInterceptor(requestInterceptor)
	    .build();
		restAdapterApi.setLogLevel(RestAdapter.LogLevel.FULL);
		JiaZhuangAppService service = restAdapterApi.create(JiaZhuangAppService.class);
//		List<User> user = service.listUser();
//		List<User> user = service.getListUser("1");

		List<User> user = service.getListUserWhere2(getData());
		
//		System.out.print(user.toString());
		for (int i=0; i<user.size(); i++) {
			System.out.print(user.get(i));
		}
	}
	
	private static Data getData() {
		Data mData = new Data();
//		mData.limit = "1";
		mData.privateId = "1";
//		mData.data = "";
//		mData.data = new ArrayList<HashMap<String,String>>();
//		HashMap<String,String> hm = new HashMap<String,String>();
//		hm.put("limit", "1");
//		mData.data.add(hm);
		mData.data = new HashMap<String,String>();
		mData.data.put("limit", "2");
		mData.data.put("order", "id DESC");
		return mData;
	}
	
	private static User myCallBack(User u) {
		return u;
	}
	
	private static void testLocal() {
		RestAdapter restAdapterApi = new RestAdapter.Builder()
	    .setEndpoint("http://127.0.0.1")
	    .build();
		restAdapterApi.setLogLevel(RestAdapter.LogLevel.FULL);
		JiaZhuangAppService service = restAdapterApi.create(JiaZhuangAppService.class);
//		List<User> user = service.listUser();
		
		List<User> user = service.listUser();
		
//		System.out.print(user.toString());
//		/*
		for (int i=0; i<user.size(); i++) {
			System.out.print(user.get(i));
		}
//		*/
	}
	
public static void main(String[] args) {
//	testLocal();
	test224();
}
}
