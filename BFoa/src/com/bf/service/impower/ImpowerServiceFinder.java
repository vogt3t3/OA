package com.bf.service.impower;

import java.util.List;

import com.bf.base.BaseFinder;
import com.bf.po.impower.Impower;
import com.bf.po.module.Module;

public interface ImpowerServiceFinder extends BaseFinder<Impower>{
	//获得权限
	public Impower findImpower(int mainBody_id,String mainBody_type,int module_id);
    //认证
	public boolean hasOption(int u_id,int m_id,String str);
	//辅助认证
	public boolean hasOptionAt(int u_id,String m_name,String str);
	
	//得到所有用户能够访问到的模块
	public List<Module> findByUser(int u_id);
}
