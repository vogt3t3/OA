package com.bf.service.doc;

import java.util.List;

import com.bf.base.BaseDao;
import com.bf.po.doc.ApproveInfo;
import com.bf.po.doc.Document;
import com.bf.po.user.User;
import com.bf.po.workFlow.WorkFlow;

public interface DocService extends BaseDao {
	// 添加公文 并绑定流程
	public void addDocForProcess(Document doc,WorkFlow wf);
	//根据公文对应的流程实例ID 找到对应的路由集合
	public List<String> findNextStepTransition(long processInstanceId);
	//提交公文（提交申请）
	public void applyDoc(String uname,Document doc,String transitionName);
	//根据用户取得待审的公文信息
	public List<Document> findApproveingDoc(User user);
	//提交审批信息
	public void addApproveInfo(User user,Document doc,ApproveInfo approveInfo);
}
