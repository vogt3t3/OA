package com.bf.service.impower;

import com.bf.base.BaseDao;
import com.bf.po.impower.Impower;

public interface ImpowerService extends BaseDao {
	//授权
	public void impower(int mainBody_id,String mainBody_type,String str,int module_id);
	
}
