package com.bf.action.group;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.bf.po.group.Group;
import com.bf.po.user.User;
import com.bf.service.group.GroupService;
import com.bf.service.group.GroupServiceFinder;
import com.bf.service.user.UserServiceFinder;

public class GroupAction {

	
	private Group grp = new Group();
	
	private int gid;
	
	private int uid;


	@Resource(name="groupServiceImpl")
	private GroupService gs;
	
	@Resource(name="groupServiceFinderImpl")
	private GroupServiceFinder gsf;
	
	@Resource(name = "userServiceFinderImpl")
	private UserServiceFinder msf;
	
	// 显示账号管理界面
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//拿出所有的组
		List<Group> groups = gsf.findAll(Group.class, "from com.bf.po.group.Group g where g.flag=1");
		//拿出所有的用户
		List<User> users = msf.findAll(User.class, "from com.bf.po.user.User u where u.flag=1");
		
		request.setAttribute("groups", groups);
		request.setAttribute("users", users);
		
		return "index";
	}

	// 显示添加组
	public String showAddGrp() {
		return "showAddGrp";
	}

	// 添加组
	public String addGrp() {
		grp.setFlag(1);
		gs.save(grp);
		return "addGrp";
	}
	
	//删除组
	public String deleteGrp() {
		gs.deleteByLogic(Group.class, new Serializable[] { gid }, "g_id", "flag");
		//gs.deleteGroup(gid);
		return "deleteGrp";
	}
	
	//打开修改组01
	public String openUpdateGroup() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Group> groups = gsf.findAll(Group.class, "from com.bf.po.group.Group g");
		User user = msf.findById(com.bf.po.user.User.class, uid);
		request.setAttribute("groups", groups);
		request.setAttribute("user", user);
		return "openUpdateGroup";
	}
	//修改组
	public String updateGroup() {
		System.out.println(gid);
		gs.updateGroup(uid, gid);
		return "updateGroup";
	}
	
	public Group getGrp() {
		return grp;
	}

	public void setGrp(Group grp) {
		this.grp = grp;
	}
	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
}
