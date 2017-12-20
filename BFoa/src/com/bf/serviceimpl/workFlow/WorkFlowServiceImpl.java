package com.bf.serviceimpl.workFlow;

import java.io.ByteArrayInputStream;

import javax.annotation.Resource;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bf.base.AbstactDao;
import com.bf.po.workFlow.WorkFlow;
import com.bf.service.workFlow.WorkFlowService;
import com.bf.service.workFlow.WorkFlowServiceFinder;

@Service
@Transactional
public class WorkFlowServiceImpl extends AbstactDao implements WorkFlowService {
	
	@Resource(name = "workFlowServiceFinderImpl")
	private WorkFlowServiceFinder wsf;
	
	public void deployProcess(byte[] processFile,byte[] processImg) {
		//将文件部署到数据库中
		ProcessDefinition pd = ProcessDefinition.parseXmlInputStream(new ByteArrayInputStream(processFile));
		JbpmContext context = getContext();
	    context.deployProcessDefinition(pd);
		//context.close();
		//把数据存储在对应的tb_workFlow中
		//去到对应的WorkFlow (根据名称取出对象)
		WorkFlow wf = wsf.findByKey(WorkFlow.class, "from WorkFlow wf where wf.wf_name=?",new Object[]{pd.getName()});
		if(wf==null) {
			wf = new WorkFlow();
			wf.setProcessFile(processFile);
			wf.setProcessImg(processImg);
			wf.setWf_name(pd.getName());
			wf.setFlag(1);
			save(wf);
			return;
		} 
		wf.setProcessFile(processFile);
		wf.setProcessImg(processImg);
		wf.setWf_name(pd.getName());
		update(wf);
	}
}
