package com.bf.serviceimpl.dep;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bf.base.AbstactDao;
import com.bf.po.dep.Department;
import com.bf.service.dep.DepService;
import com.bf.service.dep.DepServiceFinder;
@Service
public class DepServiceImpl extends AbstactDao implements DepService {
	@Resource(name = "depServiceFinderImpl")
	private DepServiceFinder dfr;
	
	public void addDep(Department dep,int pid) {
		if(pid!=0) {
			//存在父部门
			dep.setParent(dfr.findById(Department.class, pid));
			super.save(dep);
		} else {
			super.save(dep);
		}
	}
}
