package com.bf.serviceimpl.group;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bf.base.AbstactDao;
import com.bf.po.group.Group;
import com.bf.po.user.User;
import com.bf.service.group.GroupService;
import com.bf.service.group.GroupServiceFinder;
import com.bf.service.user.UserServiceFinder;

@Service
public class GroupServiceImpl extends AbstactDao implements GroupService {

	@Resource(name = "groupServiceFinderImpl")
	private GroupServiceFinder gsf;
	
	
	@Resource(name = "userServiceFinderImpl")
	private UserServiceFinder usf;
	
	// 物理删除
	public void deleteGroup(int g_id) {
		Group group = gsf.findById(Group.class, g_id);
		int gid = 0;
		if (group.getG_name().equals("公共组")) {
			gid = group.getG_id();
			System.out.println("不允许删除公共组");
		} else {
			Set<User> users = group.getUsers();
			for (User user : users) {
				user.setGroup(gsf.findById(Group.class, gid));
			}
		}
	}
	//修改组
	 public void updateGroup(int u_id,int g_id) {
		 User user = usf.findById(User.class, u_id);
		 Group group = gsf.findById(Group.class, g_id);
		 user.setGroup(group);
		 super.update(user);
	 }
}
