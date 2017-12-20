package com.bf.serviceimpl.impower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.property.MapAccessor.MapSetter;
import org.springframework.stereotype.Service;

import com.bf.base.AbstractFinder;
import com.bf.po.impower.Impower;
import com.bf.po.module.Module;
import com.bf.po.user.User;
import com.bf.service.impower.ImpowerServiceFinder;
import com.bf.service.mod.ModServiceFinder;
import com.bf.service.user.UserServiceFinder;

@Service
public class ImpowerServiceFinderImpl extends AbstractFinder<Impower> implements
		ImpowerServiceFinder {

	@Resource(name = "userServiceFinderImpl")
	private UserServiceFinder usf;

	@Resource(name = "modServiceFinderImpl")
	private ModServiceFinder msf;

	// 获得权限
	public Impower findImpower(int mainBody_id, String mainBody_type,
			int module_id) {
		List<Object> params = new ArrayList<Object>();
		params.add(mainBody_id);
		params.add(mainBody_type);
		params.add(module_id);
		Impower impower = super
				.findByKey(
						Impower.class,
						"from Impower ir where ir.mainBody_id=? and ir.mainBody_type=? and ir.module_id=?",
						params.toArray());
		return impower;
	}

	// 认证
	public boolean hasOption(int u_id, int m_id, String str) {
		boolean flag = false;
		// 取得用户的权限
		Impower impower_user = findImpower(u_id, Impower.USER_TYPE, m_id);
		if (impower_user != null) {
			if (str.equals("a")) {
				int index = impower_user.getSaveOption();
				if (index == 1) {
					flag = true;
				}
			}
			if (str.equals("d")) {
				int index = impower_user.getDeleteOption();
				if (index == 1) {
					flag = true;
				}
			}
			if (str.equals("r")) {
				int index = impower_user.getQueryOption();
				if (index == 1) {
					flag = true;
				}
			}
			if (str.equals("u")) {
				int index = impower_user.getUpdateOption();
				if (index == 1) {
					flag = true;
				}
			}
		}
		// 通过用户得组ID
		User user = usf.findById(User.class, u_id);
		int g_id = user.getGroup().getG_id();

		// 得到组对应的权限
		Impower impower_group = findImpower(g_id, Impower.GROUP_TYPE, m_id);
		if (impower_group != null) {
			if (str.equals("a")) {
				int index = impower_group.getSaveOption();
				if (index == 1) {
					flag = true;
				}
			}
			if (str.equals("r")) {
				int index = impower_group.getQueryOption();
				if (index == 1) {
					flag = true;
				}
			}
			if (str.equals("u")) {
				int index = impower_group.getUpdateOption();
				if (index == 1) {
					flag = true;
				}
			}
			if (str.equals("d")) {
				int index = impower_group.getDeleteOption();
				if (index == 1) {
					flag = true;
				}
			}
		}
		return flag;
	}

	// 辅助认证
	public boolean hasOptionAt(int u_id, String m_name, String str) {
		List<Object> params = new ArrayList<Object>();
		params.add(m_name);
		Module module = msf.findByKey(Module.class,
				"from Module m where m.m_name=?", params.toArray());
		int m_id = module.getM_id();
		return hasOption(u_id, m_id, str);
	}

	// 得到用户可以访问的模块 (组的权限覆盖了用户的权限)
	public List<Module> findByUser(int u_id) {
		Map<Integer, Impower> maps = new HashMap<Integer, Impower>();
		

		// 得到组的权限列表
		User user = usf.findById(User.class, u_id);
		int g_id = user.getGroup().getG_id();
		List<Impower> g_imps = findByGroupId(g_id);
		for (Impower g_imp : g_imps) {
			maps.put(g_imp.getModule_id(), g_imp);
		}
		//得到用户独立的权限列表
		List<Impower> u_imps = findByUserId(u_id, 0);
		for(Impower u_imp : u_imps) {
			maps.put(u_imp.getModule_id(), u_imp);
		}
		
		// 得到一个集合 用于装没有查看操作权限对象的模块ID
		List<Integer> nrID = new ArrayList<Integer>();
		Set<Map.Entry<Integer, Impower>> entrys = maps.entrySet();
		for (Map.Entry<Integer, Impower> entry : entrys) {
			Impower imp = entry.getValue();
			if (imp.getQueryOption() == 0) {
				nrID.add(entry.getKey());
			}
		}
		// 删除没有查看功能的模块
		for (Integer key : nrID) {
			maps.remove(key);
		}
		// 如果没有模块
		if (maps.isEmpty()) {
			return new ArrayList<Module>();
		}
		Set<Integer> sets = maps.keySet();
		String hql = "from Module m where m.m_id in (:ids)";
		List<Module> mods = msf.findAllByCollection(Module.class, hql, sets);
		return mods;
	}

	// 根据用户得到所有的权限列表
	private List<Impower> findByUserId(int u_id) {
		List<Object> params = new ArrayList<Object>();
		params.add(u_id);
		params.add(Impower.USER_TYPE);
		return super
				.findAllKey(
						Impower.class,
						"from Impower imp where imp.mainBody_id=? and imp.mainBody_type=?",
						params.toArray());
	}

	// 根据组的所有权限列表
	private List<Impower> findByGroupId(int g_id) {
		List<Object> params = new ArrayList<Object>();
		params.add(g_id);
		params.add(Impower.GROUP_TYPE);
		return super
				.findAllKey(
						Impower.class,
						"from Impower imp where imp.mainBody_id=? and imp.mainBody_type=?",
						params.toArray());
	}

	// 加一个方法 (得到用户独立的权限)
	private List<Impower> findByUserId(int u_id, int extOption) {
		List<Object> params = new ArrayList<Object>();
		params.add(u_id);
		params.add(Impower.USER_TYPE);
		params.add(extOption);
		return super
				.findAllKey(
						Impower.class,
						"from Impower imp where imp.mainBody_id=? and imp.mainBody_type=? and imp.extOption=?",
						params.toArray());
	}

}
