package com.bf.action.emp;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.bf.common.PageView;
import com.bf.po.dep.Department;
import com.bf.po.emp.Employee;
import com.bf.service.dep.DepServiceFinder;
import com.bf.service.emp.EmpService;
import com.bf.service.emp.EmpServiceFinder;

public class EmpAction {

	@Resource(name = "depServiceFinderImpl")
	private DepServiceFinder dfr;

	@Resource(name = "empServiceFinderImpl")
	private EmpServiceFinder efr;

	@Resource(name = "empServiceImpl")
	private EmpService ef;
	// 上传的文件
	private File image;
	// 上传文件的名称
	private String imageFileName;

	private Employee emp = new Employee();

	// 部门ID
	private String depId;

	// 显示用户信息
	public String execute() {
		// 得到员工的分页信息
		HttpServletRequest request = ServletActionContext.getRequest();
		int pageSize = 2;
		int pageNo = 0;
		String pageNo_str = request.getParameter("pager.offset");
		if (pageNo_str != null) {
			pageNo = Integer.parseInt(pageNo_str);
		}
		PageView<Employee> pv = efr.findByPage(Employee.class,
				"from Employee e where e.flag=1", pageNo, pageSize);
		request.setAttribute("pv", pv);
		// 得到父部门的所有信息
		List<Department> deps = dfr.findAll(Department.class,
				"from Department d where d.parent=null");
		List<Department> subDeps = dfr.findAll(Department.class,
				"from Department d where d.parent!=null");
		request.setAttribute("deps", deps);
		request.setAttribute("subDeps", subDeps);
		return "index";
	}

	// 添加员工信息
	public String addEmp() {
		if (image != null) {
			String realPath = ServletActionContext.getServletContext()
					.getRealPath("/web/imgs/emp");
			// 得到目标文件
			File saveFile = new File(new File(realPath), imageFileName);
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			try {
				FileUtils.copyFile(image, saveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			int dep_id = 0;
			if (depId != null) {
				dep_id = Integer.parseInt(depId);
			}
			emp.setDep(dfr.findById(Department.class, dep_id));
			emp.setFlag(1);
			emp.setEmp_img("web/imgs/emp/" + imageFileName);
			ef.save(emp);

		}
		return "addEmp";
	}

	// 删除员工
	public String deleteEmp() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] strs = request.getParameterValues("cc");
		for (String str : strs) {
			ef.deleteByLogic(Employee.class, new Serializable[] { Integer
					.parseInt(str) }, "emp_id", "flag");
		}
		return "deleteEmp";
	}

	// 显示员工信息
	public String showEmp() {
		HttpServletRequest request = ServletActionContext.getRequest();
		int empId = 0;
		String str_id = request.getParameter("empId");
		if (str_id != null) {
			empId = Integer.parseInt(str_id);
		}
		Employee emp = efr.findById(Employee.class, empId);
		request.setAttribute("emp", emp);
		return "showEmp";
	}

	// 查询员工信息
	public String findEmp() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		// 放置条件的值
		List<Object> params = new ArrayList<Object>();
		// 放置条件的key
		StringBuffer hql = new StringBuffer();

		hql.append("e.flag=?");
		params.add(new Integer(1));
		String job = request.getParameter("job");
		
		if (job != null && !job.equals("") ) {
			if (params.size() > 0) {
				hql.append(" and ");
				hql.append("e.emp_job=?");
				params.add(job.trim());
				//把值传到页面上去
				request.setAttribute("job", job);
			}
		}
		String job01 = request.getParameter("job01");
		if(job01!=null) {
			if (params.size() > 0) {
				//编码
				job01 = new String(job01.getBytes("iso-8859-1"),"utf-8");
				hql.append(" and ");
				hql.append("e.emp_job=?");
				params.add(job01.trim());
				//把值传到页面上去
				request.setAttribute("job", job01);
			}
		}
		
		
		String name = request.getParameter("name");
		if (name != null && !name.equals("")) {
			if (params.size() > 0) {
				hql.append(" and ");
				hql.append("e.emp_name=?");
				params.add(name.trim());
				//把值传到页面上去
				request.setAttribute("name", name);
			}
		}
		String name01 = request.getParameter("name01");
		if(name01!=null) {
			if (params.size() > 0) {
				//编码
				name01 = new String(name01.getBytes("iso-8859-1"),"utf-8");
				hql.append(" and ");
				hql.append("e.emp_name=?");
				params.add(name01.trim());
				//把值传到页面上去
				request.setAttribute("name", name01);
			}
		}
		
		String subDepId_str = request.getParameter("dep");
		if (subDepId_str != null && !subDepId_str.equals("请选择")) {
			int subDepId = Integer.parseInt(subDepId_str);
			if (params.size() > 0) {
				hql.append(" and ");
				hql.append("e.dep.dep_id=?");
				params.add(new Integer(subDepId));
				//把值传到页面上去
				request.setAttribute("dep", subDepId);
			}
		}
		String address = request.getParameter("address");
		if (address != null && !address.equals("") ) {
			if (params.size() > 0) {
				hql.append(" and ");
				hql.append("e.emp_address=?");
				params.add(address.trim());
				//把值传到页面上去
				request.setAttribute("address", address);
			}
		}
		int pageSize = 3;
		int pageNo = 0;
		String pageNo_str = request.getParameter("pager.offset");
		if (pageNo_str != null) {
			pageNo = Integer.parseInt(pageNo_str);
		}
		PageView<Employee> pv = efr.findByPage(Employee.class,
				"from Employee e where " + hql.toString(), params.toArray(),
				pageNo, pageSize);
		request.setAttribute("pv", pv);
		return "findEmp";
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

}
