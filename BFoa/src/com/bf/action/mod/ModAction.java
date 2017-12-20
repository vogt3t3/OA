package com.bf.action.mod;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.bf.common.PageView;
import com.bf.po.module.Module;
import com.bf.service.mod.ModService;
import com.bf.service.mod.ModServiceFinder;

public class ModAction {
	

	@Resource(name = "modServiceFinderImpl")
	private ModServiceFinder msf;
	
	@Resource(name = "modServiceImpl")
	private ModService mds;

	private Module mod = new Module();
	
	private File imgC;
	
    private File imgN;
    
    private String imgCFileName;
    
    private String imgNFileName;
	// 分页显示模块
	public String execute() {
		// 显示父模块 单击父模块显示子模块
		HttpServletRequest request = ServletActionContext.getRequest();
		int mid = 0;
		String mid_str = request.getParameter("mid");
		if (mid_str != null) {
			mid = Integer.parseInt(mid_str);
		}
		int pageSize = 3;
		int pageNo = 0;
		String pageNo_str = request.getParameter("pager.offset");
		if (pageNo_str != null) {
			pageNo = Integer.parseInt(pageNo_str);
		}
		PageView<Module> pv = msf.findPageByFlag(mid, pageNo, pageSize);
		request.setAttribute("pv", pv);
		return "index";
	}

	// 显示添加模块
	public String openAddMod() {
		// 查出所有的父模块
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Module> mods = msf.findAll(Module.class,
				"from Module m where m.parent=null");
		request.setAttribute("mods", mods);
		return "openAddMod";
	}
	
	//添加模块
	public String addMod() throws Exception {
		if(imgC!=null && imgN!=null) {
			String realPath = ServletActionContext.getServletContext().getRealPath("/web/imgs/bar");
			File saveFileC = new File(new File(realPath),imgCFileName);
			File saveFileN = new File(new File(realPath),imgNFileName);
			if(!saveFileC.getParentFile().exists() && !saveFileN.getParentFile().exists()) {
				saveFileC.getParentFile().mkdirs();
				saveFileN.getParentFile().mkdirs();
			}
			FileUtils.copyFile(imgC, saveFileC);
			FileUtils.copyFile(imgC, saveFileN);
		}
		if(mod.getM_id()!=0) {
			mod.setParent(msf.findById(Module.class, mod.getM_id()));
		}
		mod.setM_path_c("web/imgs/bar/"+imgCFileName);
		mod.setM_path_n("web/imgs/bar/"+imgNFileName);
		mod.setFlag(1);
		mds.save(mod);
		return "addMod";
	}
   
	//显示父级模块
	public String findParentMod() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<Module> parentMods = msf.findAll(Module.class,
				"from Module m where m.parent=null");
		request.setAttribute("parentMods", parentMods);
		return "findParentMod";
	}
	//根据父级模块ID找到对应的子模块
	public String findSubMod() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int mid = 0;
		String mid_str = request.getParameter("mid");
		if(mid_str!=null) {
			mid = Integer.parseInt(mid_str);
		}
		List<Module> subMods = msf.findAll(Module.class, "from Module m where m.parent.m_id="+mid);
		request.setAttribute("subMods", subMods);
		return "findSubMod";
	}
	public Module getMod() {
		return mod;
	}

	public void setMod(Module mod) {
		this.mod = mod;
	}
	public File getImgC() {
		return imgC;
	}

	public void setImgC(File imgC) {
		this.imgC = imgC;
	}

	public File getImgN() {
		return imgN;
	}

	public void setImgN(File imgN) {
		this.imgN = imgN;
	}

	public String getImgCFileName() {
		return imgCFileName;
	}

	public void setImgCFileName(String imgCFileName) {
		this.imgCFileName = imgCFileName;
	}

	public String getImgNFileName() {
		return imgNFileName;
	}

	public void setImgNFileName(String imgNFileName) {
		this.imgNFileName = imgNFileName;
	}
}
