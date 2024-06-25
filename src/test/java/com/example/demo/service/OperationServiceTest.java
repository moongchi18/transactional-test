package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.transaction.annotation.Propagation;

import com.example.demo.entity.Operation;
import com.example.demo.repository.OperationRepository;

@SpringBootTest
class OperationServiceTest {

	@Autowired
	OperationService service;
	
	@Autowired 
	OperationRepository repository;
	
	static Map<String, String> map = new HashMap<String, String>();
	static Exception exception;
	@BeforeAll
	static void setUp() {
		exception = new RuntimeException();
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("============================================");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			System.err.println(key + " : " + val);
		}
	}
	
	@Test
	void REQUIRES_NEW() {
		Operation op = Operation.of("REQUIRES_NEW");
		try {
			service.saveWithException(op, exception, Propagation.REQUIRES_NEW);
		} catch (Exception e) {
			System.err.println(op.getOperation() + " Exception : " + e.getClass());
		} 
		Optional<Operation> findedOperation = repository.findByOperation(op.getOperation());
		String result = findedOperation.isPresent() ? "COMMIT : " + findedOperation.get() :"ROLLBACK";
		
		System.err.println(op.getOperation() + " - RESULT : " + result);
		map.put(exception.getClass().getSimpleName() + "_" + op.getOperation(), result);	
	}
	@Test
	void REQUIRED() {
		Operation op = Operation.of("REQUIRED");
		try {
			service.saveWithException(op, exception, Propagation.REQUIRED);
		} catch (Exception e) {
			System.err.println(op.getOperation() + " Exception : " + e.getClass());
		}
		Optional<Operation> findedOperation = repository.findByOperation(op.getOperation());
		String result = findedOperation.isPresent() ? "COMMIT : " + findedOperation.get() :"ROLLBACK";
		
		System.err.println(op.getOperation() + " - RESULT : " + result);
		map.put(exception.getClass().getSimpleName() + "_" + op.getOperation(), result);	
	}
	@Test
	void SUPPORTS() {
		Operation op = Operation.of("SUPPORTS");
		try {
			service.saveWithException(op, exception, Propagation.SUPPORTS);
		} catch (Exception e) {
			System.err.println(op.getOperation() + " Exception : " + e.getClass());
		}
		Optional<Operation> findedOperation = repository.findByOperation(op.getOperation());
		String result = findedOperation.isPresent() ? "COMMIT : " + findedOperation.get() :"ROLLBACK";
		
		System.err.println(op.getOperation() + " - RESULT : " + result);
		map.put(exception.getClass().getSimpleName() + "_" + op.getOperation(), result);	

	}
	@Test
	void NOT_SUPPORTED() {
		Operation op = Operation.of("NOT_SUPPORTED");
		try {
			service.saveWithException(op, exception, Propagation.NOT_SUPPORTED);
		} catch (Exception e) {
			System.err.println(op.getOperation() + " Exception : " + e.getClass());
		}
		Optional<Operation> findedOperation = repository.findByOperation(op.getOperation());
		String result = findedOperation.isPresent() ? "COMMIT : " + findedOperation.get() :"ROLLBACK";
		
		System.err.println(op.getOperation() + " - RESULT : " + result);
		map.put(exception.getClass().getSimpleName() + "_" + op.getOperation(), result);	
	}
	@Test
	void INTERNAL_METHOD() {
		Operation op = Operation.of("INTERNAL_METHOD");
		try {
			service.saveWithException(op, exception, null);
		} catch (Exception e) {
			System.err.println(op.getOperation() + " Exception : " + e.getClass());
		} 
		Optional<Operation> findedOperation = repository.findByOperation(op.getOperation());
		String result = findedOperation.isPresent() ? "COMMIT : " + findedOperation.get() :"ROLLBACK";
		
		System.err.println(op.getOperation() + " - RESULT : " + result);
		map.put(exception.getClass().getSimpleName() + "_" + op.getOperation(), result);	
	}

}
