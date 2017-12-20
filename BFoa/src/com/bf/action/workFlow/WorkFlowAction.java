package com.bf.action.workFlow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.io.SAXReader;

import com.bf.po.workFlow.WorkFlow;
import com.bf.service.workFlow.WorkFlowService;
import com.bf.service.workFlow.WorkFlowServiceFinder;


public class WorkFlowAction {
	@Resource(name = "workFlowServiceFinderImpl")
	private WorkFlowServiceFinder wsf;
	
	@Resource(name = "workFlowServiceImpl")
	private WorkFlowService ws;
	
	//上传的文件
	private File processFile;
	
	private File processImg;
	
	//工作流ID
	private int wf_id;
	
	
	
	//显示流程(打开显示流程页面)
	public String showwf() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<WorkFlow> wfs = wsf.findAll(WorkFlow.class, "from WorkFlow");
		request.setAttribute("wfs", wfs);
		return "showwf";
	}
	//部署流程
	public String deployProcess() {
		//把数据放在数据库中
		//把XML文件部署到数据库中
		ws.deployProcess(getBytesFromFile(processFile), getBytesFromFile(processImg));
		return "deployProcess";
	}
	
	//打开查看流程页面
	public String swf() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<WorkFlow> wfs = wsf.findAll(WorkFlow.class, "from WorkFlow");
		request.setAttribute("wfs", wfs);
		return "swf";
	}
	//查看文件
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//清缓存
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//通过ID得对象
		WorkFlow wf = wsf.findById(WorkFlow.class, wf_id);
		byte[] defs = wf.getProcessFile();
		String wfmString = new SAXReader().read(new ByteArrayInputStream(defs)).asXML();
		out.print(wfmString);
		return null;
	}
	//查看图片
	public String showImg() {
		HttpServletResponse response = ServletActionContext.getResponse();
		WorkFlow wf = wsf.findById(WorkFlow.class, wf_id);
		try {
			response.getOutputStream().write(wf.getProcessImg());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "showImg";
	}
	
	
	public File getProcessFile() {
		return processFile;
	}
	public void setProcessFile(File processFile) {
		this.processFile = processFile;
	}
	public File getProcessImg() {
		return processImg;
	}
	public void setProcessImg(File processImg) {
		this.processImg = processImg;
	}
	public int getWf_id() {
		return wf_id;
	}
	public void setWf_id(int wfId) {
		wf_id = wfId;
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
