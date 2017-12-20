package com.bf.action.impower;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.bf.po.group.Group;
import com.bf.po.impower.Impower;
import com.bf.po.module.Module;
import com.bf.po.user.User;
import com.bf.service.group.GroupServiceFinder;
import com.bf.service.impower.ImpowerService;
import com.bf.service.impower.ImpowerServiceFinder;
import com.bf.service.mod.ModServiceFinder;
import com.bf.service.user.UserServiceFinder;

public class ImpowerAction {
	@Resource(name = "impowerServiceImpl")
	private ImpowerService is;

	@Resource(name = "impowerServiceFinderImpl")
	private ImpowerServiceFinder isf;
	
	@Resource(name="groupServiceFinderImpl")
	private GroupServiceFinder gsf;
	
	@Resource(name = "userServiceFinderImpl")
	private UserServiceFinder usf;
	
	@Resource(name = "modServiceFinderImpl")
	private ModServiceFinder msf;
	
	private Impower impower = new Impower();
	
	private int mainBody_id;
	
	private String mainBody_type;
	
	private String str;
	
	private int module_id;
	
	//显示权限页面
	public String showImpowerView() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if(mainBody_type.equals("group")) {
			Group group = gsf.findById(Group.class, mainBody_id);
			request.setAttribute("group", group);
		} else if(mainBody_type.equals("user")) {
			User user = usf.findById(User.class, mainBody_id);
			request.setAttribute("user", user);
		}
		//得到所有的父模块
		List<Module> modules = msf.findAll(Module.class, "from Module m where m.parent=null");
		request.setAttribute("modules", modules);
		return "showImpowerView";
	}
	//授权
	public String impower() {
		is.impower(mainBody_id, mainBody_type, str, module_id);
		return "impower";
	}
	
	//得到对应的权限
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//清缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml;charset=utf-8");
		
		try {
			PrintWriter out = response.getWriter();
			Impower impower = isf.findImpower(mainBody_id, mainBody_type, module_id);
			if(impower!=null) {
				out.println("<impowers>");
				    out.println("<impower-saveOption>"+impower.getSaveOption()+"</impower-saveOption>");
				    out.println("<impower-queryOption>"+impower.getQueryOption()+"</impower-queryOption>");
				    out.println("<impower-updateOption>"+impower.getUpdateOption()+"</impower-updateOption>");
				    out.println("<impower-deleteOption>"+impower.getDeleteOption()+"</impower-deleteOption>");
				    out.println("<impower-extOption>"+impower.getExtOption()+"</impower-extOption>");
				out.println("</impowers>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//得到登陆用户的模块
	public String getUsetModules() {
		//得到session中用户的ID
		HttpServletRequest request = ServletActionContext.getRequest();
		
		User user = (User)request.getSession().getAttribute("person");
		
		int uid = user.getU_id();
		
		List<Module> modules = isf.findByUser(uid);
		
		request.setAttribute("modules", modules);
		
		return "getUsetModules";
		
	}
	
	public Impower getImpower() {
		return impower;
	}

	public void setImpower(Impower impower) {
		this.impower = impower;
	}
	public int getMainBody_id() {
		return mainBody_id;
	}

	public void setMainBody_id(int mainBodyId) {
		mainBody_id = mainBodyId;
	}

	public String getMainBody_type() {
		return mainBody_type;
	}

	public void setMainBody_type(String mainBodyType) {
		mainBody_type = mainBodyType;
	}
	
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public int getModule_id() {
		return module_id;
	}
	public void setModule_id(int moduleId) {
		module_id = moduleId;
	}
}
