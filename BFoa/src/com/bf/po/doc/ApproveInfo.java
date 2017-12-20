package com.bf.po.doc;

import java.util.Date;

import com.bf.po.user.User;

/**
 * 审批信息
 * 
 * @author Administrator
 * 
 */
public class ApproveInfo {
	public int getAo_id() {
		return ao_id;
	}

	public void setAo_id(int aoId) {
		ao_id = aoId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	private int ao_id;
	private String comment;
	private Date approveTime;
	private Document document;
	private User approver;
}
