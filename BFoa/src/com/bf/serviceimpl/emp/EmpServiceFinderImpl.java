package com.bf.serviceimpl.emp;

import org.springframework.stereotype.Service;

import com.bf.base.AbstractFinder;
import com.bf.po.emp.Employee;
import com.bf.service.emp.EmpServiceFinder;
@Service
public class EmpServiceFinderImpl extends AbstractFinder<Employee> implements EmpServiceFinder {

}
