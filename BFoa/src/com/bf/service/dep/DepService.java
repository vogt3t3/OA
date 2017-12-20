package com.bf.service.dep;

import com.bf.base.BaseDao;
import com.bf.po.dep.Department;

public interface DepService extends BaseDao {
   //添加部门
	public void addDep(Department dep,int pid);
}
