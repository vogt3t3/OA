package com.bf.action.doc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.bf.po.doc.ApproveInfo;
import com.bf.po.doc.Document;
import com.bf.po.user.User;
import com.bf.po.workFlow.WorkFlow;
import com.bf.service.approve.ApproveServiceFinder;
import com.bf.service.doc.DocService;
import com.bf.service.doc.DocServiceFinder;
import com.bf.service.workFlow.WorkFlowServiceFinder;

public class DocAction {
	

	@Resource(name = "docServiceFinderImpl")
	private DocServiceFinder dsf;
	
	@Resource(name = "docServiceImpl")
	private DocService ds;
	
	@Resource(name = "workFlowServiceFinderImpl")
	private WorkFlowServiceFinder wsf;
	
	@Resource(name = "approveServiceFinderImpl")
	private ApproveServiceFinder asf;
	
	private int workFlowId;
	
	private String doc_title;
	
	private String doc_desc;
	
	private File content;
	
	private String contentFileName;
	
	private int doc_id;
	
	private String transitionName;
	
	private String comment;


	

	//显示添加公文页面01
	public String addDoc() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<WorkFlow> wfs = wsf.findAll(WorkFlow.class, "from WorkFlow");
		request.setAttribute("wfs", wfs);
		return "addDoc";
	}
	
	//显示添加公文页面02
	public String addDoc02() {
		HttpServletRequest request = ServletActionContext.getRequest();
		WorkFlow workFlow = wsf.findById(WorkFlow.class, workFlowId);
		request.setAttribute("workFlow", workFlow);
		return "addDoc02";
	}
	
	//添加公文
	public String addDoc03() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//1：将公文的信息添加到数据库中 2：公文和流程进行绑定
		Document doc = new Document();
		doc.setCreateTime(new Date());
		doc.setDoc_desc(doc_desc);
		doc.setDoc_title(doc_title);
		doc.setLoadName(contentFileName);
		doc.setContent(getBytesFromFile(content));
		doc.setFlag(1);
		doc.setStatus(Document.STATUS_NEW);
		User user = (User)request.getSession().getAttribute("person");
		doc.setCreator(user);
		WorkFlow wf = wsf.findById(WorkFlow.class, workFlowId);
		ds.addDocForProcess(doc, wf);
		return "addDoc03";
	}
	//显示我的公文
	public String myDoc() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)request.getSession().getAttribute("person");
		int u_id = user.getU_id();
		List<Document> myDocs = dsf.findAllKey(Document.class, "from Document doc where doc.creator.u_id=?", new Object[]{u_id});
		request.setAttribute("myDocs", myDocs);
		return "myDoc";
	}
	
	//下载
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		Document doc = dsf.findById(Document.class, doc_id);
		response.reset();
		String loadName = doc.getLoadName();
		String name = new String(loadName.getBytes("utf-8"),"iso-8859-1");
		response.setContentType("application/x-download;charset=utf-8");
		response.setHeader("content-Disposition", "attachment;filename="+name);
		response.getOutputStream().write(doc.getContent());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	}
	//打开提交公文
	public String openSubmitDoc() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Document doc = dsf.findById(Document.class, doc_id);
		long processInstanceId = doc.getProcessInstanceId();
		List<String> transitions = ds.findNextStepTransition(processInstanceId);
		request.setAttribute("transitions", transitions);
		request.setAttribute("doc_id", doc_id);
		return "openSubmitDoc";
	}
	
	//提交公文
	public String submitDoc() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)request.getSession().getAttribute("person"); 
		String uname = user.getU_name();
		Document doc = dsf.findById(Document.class, doc_id);
		ds.applyDoc(uname, doc, transitionName);
		return "submitDoc";
	}
	
	//打开待审公文
	public String openApproveingDoc() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)request.getSession().getAttribute("person");
		List<Document> appingDocs = ds.findApproveingDoc(user);
		request.setAttribute("appingDocs",appingDocs);
		return "openApproveingDoc";
	}
	
	//打开审批意见
	public String openApprove() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("doc_id", doc_id);
		return "openApprove";
	}
	//提交审批信息
	public String approve() {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		User user = (User)request.getSession().getAttribute("person"); 
		
		Document doc = dsf.findById(Document.class, doc_id);
		
		ApproveInfo approveInfo = new ApproveInfo();
		approveInfo.setApproveTime(new Date());
		approveInfo.setComment(comment);
		
		ds.addApproveInfo(user, doc, approveInfo);
		
		return "approve";
	}
	
	//打开已审公文的页面
	public String openApprovedDoc() {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User)request.getSession().getAttribute("person");
		int u_id = user.getU_id();
	    List<ApproveInfo> approves = asf.findAll(ApproveInfo.class, "from ApproveInfo ao where ao.approver.u_id="+u_id);
	    request.setAttribute("approves", approves);
		return "openApprovedDoc";
	}
	
	//查看审批历史
	public String openApprovedHistory() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//根据公文ID 找到对应的approveInfo的内容
		List<ApproveInfo> approveHistory = asf.findAll(ApproveInfo.class, "from ApproveInfo ao where ao.document.dom_id="+doc_id);
		request.setAttribute("approveHistory", approveHistory);
		return "openApprovedHistory";
	}
	
	public int getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(int workFlowId) {
		this.workFlowId = workFlowId;
	}
	
	public String getDoc_title() {
		return doc_title;
	}

	public void setDoc_title(String docTitle) {
		doc_title = docTitle;
	}

	public String getDoc_desc() {
		return doc_desc;
	}

	public void setDoc_desc(String docDesc) {
		doc_desc = docDesc;
	}

	public File getContent() {
		return content;
	}

	public void setContent(File content) {
		this.content = content;
	}

	public String getContentFileName() {
		return contentFileName;
	}

	public void setContentFileName(String contentFileName) {
		this.contentFileName = contentFileName;
	}
	public int getDoc_id() {
		return doc_id;
	}

	public void setDoc_id(int docId) {
		doc_id = docId;
	}
	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	//文件转换为字节数组
	private byte[] getBytesFromFile(File file) {
		byte[] bte = null;
		try {
			if(file==null) {
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4000);
			byte[] b = new byte[4000];
			int n;
			while((n=in.read(b))!=-1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
			bte = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bte;
	}
	
}
