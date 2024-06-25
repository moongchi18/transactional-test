package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExceptionService {

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T extends Exception> void executeExceptionWithRequiresNew(T exception) throws T {
		throw exception;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public <T extends Exception> void executeExceptionWithRequired(T exception) throws T {
		throw exception;
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public <T extends Exception> void executeExceptionWithSupports(T exception) throws T {
		throw exception;
	}
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public <T extends Exception> void executeExceptionWithNotSupported(T exception) throws T {
		throw exception;
	}
}
