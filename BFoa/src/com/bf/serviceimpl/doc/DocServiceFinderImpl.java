package com.bf.serviceimpl.doc;

import org.springframework.stereotype.Service;

import com.bf.base.AbstractFinder;
import com.bf.po.doc.Document;
import com.bf.service.doc.DocServiceFinder;

@Service
public class DocServiceFinderImpl extends AbstractFinder<Document> implements
		DocServiceFinder {
	
}
