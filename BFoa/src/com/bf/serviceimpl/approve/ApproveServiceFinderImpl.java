package com.bf.serviceimpl.approve;

import org.springframework.stereotype.Service;

import com.bf.base.AbstractFinder;
import com.bf.po.doc.ApproveInfo;
import com.bf.service.approve.ApproveServiceFinder;

@Service
public class ApproveServiceFinderImpl extends AbstractFinder<ApproveInfo> implements
		ApproveServiceFinder {
}
