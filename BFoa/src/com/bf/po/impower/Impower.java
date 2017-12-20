package com.bf.po.impower;

public class Impower {
	public int getIm_id() {
		return im_id;
	}

	public void setIm_id(int imId) {
		im_id = imId;
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

	public int getModule_id() {
		return module_id;
	}

	public void setModule_id(int moduleId) {
		module_id = moduleId;
	}

	public int getSaveOption() {
		return saveOption;
	}

	public void setSaveOption(int saveOption) {
		this.saveOption = saveOption;
	}

	public int getQueryOption() {
		return queryOption;
	}

	public void setQueryOption(int queryOption) {
		this.queryOption = queryOption;
	}

	public int getUpdateOption() {
		return updateOption;
	}

	public void setUpdateOption(int updateOption) {
		this.updateOption = updateOption;
	}

	public int getDeleteOption() {
		return deleteOption;
	}

	public void setDeleteOption(int deleteOption) {
		this.deleteOption = deleteOption;
	}
	
	public int getExtOption() {
		return extOption;
	}

	public void setExtOption(int extOption) {
		this.extOption = extOption;
	}

	public static final String GROUP_TYPE = "group";
	public static final String USER_TYPE = "user";
	private int im_id;
	private int mainBody_id;
	private String mainBody_type;
	private int module_id;
	private int saveOption;
	private int queryOption;
	private int updateOption;
	private int deleteOption;
	
	//组优先字段 (如果为1 权限按照组的权限 如果为0 则按照用户权限)
	private int extOption;
    
	//授权
	public void setOption(String str) {
		this.setDeleteOption(0);
		this.setQueryOption(0);
		this.setSaveOption(0);
		this.setUpdateOption(0);
		this.setExtOption(0);
		String[] strs = str.split(",");
		for(int i=0;i<strs.length;i++) {
			if("a".equals(strs[i])) {
				this.setSaveOption(1);
			}else if("r".equals(strs[i])) {
				this.setQueryOption(1);
			} else if("u".equals(strs[i])) {
				this.setUpdateOption(1);
			} else if("d".equals(strs[i])) {
				this.setDeleteOption(1);
			} else if("e".equals(strs[i])) {
				this.setExtOption(1);
			}
		}
	}
	
}
