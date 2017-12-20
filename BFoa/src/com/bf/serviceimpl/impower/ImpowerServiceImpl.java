package com.bf.serviceimpl.impower;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bf.base.AbstactDao;
import com.bf.po.impower.Impower;
import com.bf.po.module.Module;
import com.bf.service.impower.ImpowerService;
import com.bf.service.impower.ImpowerServiceFinder;
import com.bf.service.mod.ModServiceFinder;

@Service
public class ImpowerServiceImpl extends AbstactDao implements ImpowerService {

	@Resource(name = "impowerServiceFinderImpl")
	private ImpowerServiceFinder isf;

	public void impower(int mainBody_id, String mainBody_type, String str,
			int module_id) {
		// 查看是否有权限
		Impower impower = findImpower(mainBody_id, mainBody_type, module_id);
		if (impower == null) {
			impower = new Impower();
			impower.setMainBody_id(mainBody_id);
			impower.setMainBody_type(mainBody_type);
			impower.setModule_id(module_id);
			impower.setOption(str);
			super.save(impower);
			return;
		}
		impower.setOption(str);
		super.update(impower);
	}

	// 取到权限对象
	private Impower findImpower(int mainBody_id, String mainBody_type,
			int module_id) {
		// 根据条件查询权限
		List<Object> params = new ArrayList<Object>();
		params.add(mainBody_id);
		params.add(mainBody_type);
		params.add(module_id);
		Impower impower = isf
				.findByKey(
						Impower.class,
						"from Impower ir where ir.mainBody_id=? and ir.mainBody_type=? and ir.module_id=?",
						params.toArray());
		return impower;
	}
}
