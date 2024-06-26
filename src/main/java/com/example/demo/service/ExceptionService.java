package com.example.demo.service;

import com.example.demo.exception.ChildException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExceptionService {

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void executeExceptionWithRequiresNew(Exception exception) throws Exception {
		throw exception;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void executeExceptionWithRequired(Exception exception) throws Exception {
		throw exception;
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public void executeExceptionWithSupports(Exception exception) throws Exception {
		throw exception;
	}
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void executeExceptionWithNotSupported(Exception exception) throws Exception {
		throw exception;
	}
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = ChildException.class)
	public void executeCustomExceptionWithRequired(Exception exception) throws Exception {
		throw exception;
	}
}
