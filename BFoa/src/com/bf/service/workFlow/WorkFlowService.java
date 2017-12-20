package com.bf.service.workFlow;

import com.bf.base.BaseDao;

public interface WorkFlowService extends BaseDao {
   //部署流程
	public void deployProcess(byte[] processFile,byte[] processImg);
}
