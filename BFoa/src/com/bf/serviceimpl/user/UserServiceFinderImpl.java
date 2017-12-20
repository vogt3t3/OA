package com.bf.serviceimpl.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bf.base.AbstractFinder;
import com.bf.po.user.User;
import com.bf.service.user.UserServiceFinder;

@Service
public class UserServiceFinderImpl extends AbstractFinder<User> implements
		UserServiceFinder {
	public User login(String uname, String pwd) {
		User user = new User();
		List<Object> params = new ArrayList<Object>();
		params.add(uname);
		params.add(pwd);
		user = (User)super.findByKey(User.class, "from com.bf.po.user.User u where u.u_name=? and u.u_pwd=?", params.toArray());
		return user;
	}
}
