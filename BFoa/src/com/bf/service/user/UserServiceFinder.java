package com.bf.service.user;

import com.bf.base.BaseFinder;
import com.bf.po.user.User;

public interface UserServiceFinder extends BaseFinder<User> {
	 public User login(String uname,String pwd);
}