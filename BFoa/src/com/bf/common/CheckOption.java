package com.bf.common;

import com.bf.service.impower.ImpowerServiceFinder;

/**
 * 认证方法 (JSTL)
 * 
 * @author Administrator
 * 
 */
public class CheckOption {
	private static ImpowerServiceFinder isf;

	

	// 只能是静态方法
	public static boolean hasOption(int u_id, String m_name, String str) {
		return isf.hasOptionAt(u_id, m_name, str);
	}
	
	public void setIsf(ImpowerServiceFinder isf) {
		CheckOption.isf = isf;
	}

}
