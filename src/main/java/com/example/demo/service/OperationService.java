package com.example.demo.service;

import java.util.Optional;

import com.example.demo.exception.ChildException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Operation;
import com.example.demo.repository.OperationRepository;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperationService {

	private final OperationRepository operationRepository;
	private final ExceptionService exceptionService;

	@Transactional(propagation = Propagation.REQUIRED)
	public Optional<Operation> saveWithException(Operation operation, Exception exception, @Nullable Propagation option) throws Exception {
		Operation savedMember = operationRepository.save(operation);
		try {
			if (option == Propagation.REQUIRED) {
				System.err.println("Propagation " + Propagation.REQUIRED + " parameters : " + option);
				exceptionService.executeExceptionWithRequired(exception);
			} else if (option == Propagation.REQUIRES_NEW) {
				System.err.println("Propagation " + Propagation.REQUIRES_NEW + " parameters : " + option);
				exceptionService.executeExceptionWithRequiresNew(exception);
			} else if (option == Propagation.SUPPORTS) {
				System.err.println("Propagation " + Propagation.SUPPORTS + " parameters : " + option);
				exceptionService.executeExceptionWithSupports(exception);
			} else if (option == Propagation.NOT_SUPPORTED) {
				System.err.println("Propagation " + Propagation.NOT_SUPPORTED + " parameters : " + option);
				exceptionService.executeExceptionWithNotSupported(exception);
			} else if (exception instanceof ChildException) {
				System.err.println("ROLLBACK_FOR_CUSTOM_EXCEPTION " + Propagation.REQUIRES_NEW + " parameters : " + option);
				exceptionService.executeCustomExceptionWithRequired(exception);
			} else if (option == null) {
				System.err.println("executeInternalMethod " + Propagation.REQUIRES_NEW + " parameters : " + option);
				this.executeInternalMethod(exception);
			}
		} catch (Exception e) {
			System.err.println(option + " : " + e.getClass());
		}
		return Optional.ofNullable(savedMember);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T extends Exception> void executeInternalMethod(T exception) throws T {
		throw exception;
	}
}
