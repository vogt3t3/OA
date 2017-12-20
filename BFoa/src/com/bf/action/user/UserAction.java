package com.bf.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.lob.SerializableBlob;

import com.bf.po.emp.Employee;
import com.bf.po.group.Group;
import com.bf.po.user.User;
import com.bf.service.emp.EmpServiceFinder;
import com.bf.service.group.GroupServiceFinder;
import com.bf.service.user.UserService;
import com.bf.service.user.UserServiceFinder;


public class UserAction {
	@Resource(name = "userServiceFinderImpl")
	private UserServiceFinder usf;
	
	@Resource(name = "userServiceImpl")
	private UserService us;
	
	@Resource(name="groupServiceFinderImpl")
	private GroupServiceFinder gsf;
	
	@Resource(name = "empServiceFinderImpl")
	private EmpServiceFinder efr;
	
	
	private User user = new User();
	
	private int uid;
	
	
	
	//根据用户ID得到对应的信息
	
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//清缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml;charset=utf-8");
		
		try {
			PrintWriter out = response.getWriter();
			User user = usf.findById(com.bf.po.user.User.class, uid);
			out.println("<users>");
			out.println("<user>");
			     out.println("<user-id>"+user.getEmp().getEmp_id()+"</user-id>");
			     out.println("<user-trueName>"+user.getEmp().getEmp_name()+"</user-trueName>");
			     out.println("<user-name>"+user.getU_name()+"</user-name>");
			     out.println("<user-password>"+user.getU_pwd()+"</user-password>");
			     out.println("<user-sex>"+user.getU_pwd()+"</user-sex>");
			     out.println("<user-job>"+user.getU_pwd()+"</user-job>");
			     out.println("<user-phone>"+user.getU_pwd()+"</user-phone>");
			     out.println("<user-address>"+user.getU_pwd()+"</user-address>");
			out.println("</user>");
			out.println("</users>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//打开显示添加用户 并显示用户组
	public String openAddAccount() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Group> groups = gsf.findAll(Group.class, "from com.bf.po.group.Group g");
		request.setAttribute("groups", groups);
		return "openAddAccount";
	}
	
	//单击选择后对应的员工信息 (分页)
	public String getEmps() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Employee> emps = efr.findAll(Employee.class, "from Employee e");
		request.setAttribute("emps", emps);
		return "getEmps";
	}
	//添加账号
	public String addUser() {
		user.setFlag(1);
		us.save(user);
		return "addUser";
	}
	//删除账号
    public String deleteName() {
    	us.deleteByLogic(User.class, new Serializable[] { uid }, "u_id", "flag");
    	return "deleteName";
    }	
    
    //登陆
    public String login() {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	User person = usf.login(user.getU_name(), user.getU_pwd());
    	request.getSession().setAttribute("person", person);
    	return "login";
    }
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
}
