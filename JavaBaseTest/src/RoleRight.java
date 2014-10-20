import java.util.*;


/*
 * 这是一个权限认证的例子，使用了哈希表作为数据的存储
 */

public class RoleRight {

	public static void main(String[] args) {
		RoleRight RR = new RoleRight();
		RR.init();
		RR.print();
	    RR.print("___________________________");  
        RR.insert("presider", "10110");  
        RR.print();  
        RR.print("___________________________");  
        RR.update("presider", "10100");  
        RR.print();  
        RR.print("___________________________");  
        RR.delete("presider");  
        RR.print();  
	}

	private static Hashtable<String, String> rightList = new Hashtable<String, String>();
	//初始化数据
	public void init(){
		String[] accRoleList = {"admin","satrap","manager","user"};
		String[] rightCodeList = {"10001","1001","10021","20011"};
		for (int i=0; i<accRoleList.length; i++){
			rightList.put(accRoleList[i], rightCodeList[i]);
		}
	}
	
	/*
	 * 方法说明：获得角色权限代码
	 * 输入参数：String accRole 角色名称
	 * 返回类型：String 权限代码
	 */
	public String getRight(String accRole) {
		if (rightList.containsKey(accRole))
			return (String) rightList.get(accRole);
		else
			return null;
	}
	
	/*
	 * 方法说明：获取角色权限代码
	 * 输入参数：String accRole 角色名称
	 * 输入参数：String rightCode 角色权限代码
	 * 返回类型：void
	 */
	public void insert(String accRole, String rightCode) {
		rightList.put(accRole, rightCode);
	}
	
	/*
	 * 方法说明：删除角色权限
	 * 输入参数：String accRole 角色名称
	 * 返回类型：void
	 */
	public void delete(String accRole) {
		if (rightList.containsKey(accRole))
			rightList.remove(accRole);
	}
	
	/*
	 * 方法说明：修改角色全息代码
	 * 输入参数：String accRole 角色名称
	 * 输入参数：String rightCode 角色权限代码
	 * 返回类型：void（无）
	 */
	public void update(String accRole, String rightCode) {
		// this.delete(accRole);
		this.insert(accRole, rightCode);
	}
	
	/*
	 * 方法说明：打印哈希表中角色和代码对应表
	 * 输入参数：无
	 * 返回类型：无
	 */
	public void print() {
		Enumeration<String> RLKey = rightList.keys();
		while (RLKey.hasMoreElements()) {
			String accRole = RLKey.nextElement().toString();
			print(accRole + "=" + this.getRight(accRole));
		}
	}
	
	/*
	 * 方法说明：打印信息（过载）
	 * 输入参数：Object oPara 打印的信息内容
	 * 返回类型：无
	 */
	public void print(Object oPara) {
		System.out.println(oPara);
	}

}


