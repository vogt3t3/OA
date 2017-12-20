package com.bf.po.workFlow;

/**
 * 流程
 * 
 * @author Administrator
 * 
 */
public class WorkFlow {
	public int getWf_id() {
		return wf_id;
	}

	public void setWf_id(int wfId) {
		wf_id = wfId;
	}

	public String getWf_name() {
		return wf_name;
	}

	public void setWf_name(String wfName) {
		wf_name = wfName;
	}

	public byte[] getProcessFile() {
		return processFile;
	}

	public void setProcessFile(byte[] processFile) {
		this.processFile = processFile;
	}

	public byte[] getProcessImg() {
		return processImg;
	}

	public void setProcessImg(byte[] processImg) {
		this.processImg = processImg;
	}

	private int wf_id;
	private String wf_name;
	private byte[] processFile;
	private byte[] processImg;
	private int flag;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
