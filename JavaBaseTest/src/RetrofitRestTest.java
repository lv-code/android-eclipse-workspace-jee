

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Headers;

public class RetrofitRestTest {
	
	public static void main(String[] args) {
		testLiang();
//		testLocal();
	}

	public interface JiaZhuangAppService {
		@GET("/JiaZhuangApp.php")
		List<User> listUser();
		
		@Headers({
		    "Accept: application/json",
//		    "User-Agent: Retrofit-Sample-App"
		})
		@GET("/userPotential/getItem?privateId=1&id=1")
		List<User> getItemUser();
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
	
	private static void testLiang() {
		RestAdapter restAdapterApi = new RestAdapter.Builder()
	    .setEndpoint("http://192.168.9.22:800/index.php")
	    .build();
		restAdapterApi.setLogLevel(RestAdapter.LogLevel.FULL);
		JiaZhuangAppService service = restAdapterApi.create(JiaZhuangAppService.class);
//		List<User> user = service.listUser();
		
		List<User> user = service.getItemUser();
		
//		System.out.print(user.toString());
//		/*
		for (int i=0; i<user.size(); i++) {
			System.out.print(user.get(i));
		}
//		*/
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
}
