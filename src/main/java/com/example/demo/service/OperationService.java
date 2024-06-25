package com.example.demo.service;

import java.util.Optional;

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
	public Optional<Operation> saveWithException(Operation operation, Exception t, @Nullable Propagation option) throws Exception {
		Operation savedMember = operationRepository.save(operation);
		if (t != null) {
			try {
				if (option == Propagation.REQUIRED) {
					System.err.println("Propagation " + Propagation.REQUIRED + " : operation " + option);
					exceptionService.executeExceptionWithRequired(t);
				} else if (option == Propagation.REQUIRES_NEW) {
					System.err.println("Propagation " + Propagation.REQUIRES_NEW + " : operation " + option);
					exceptionService.executeExceptionWithRequiresNew(t); 
				} else if (option == Propagation.SUPPORTS) {
					System.err.println("Propagation " + Propagation.SUPPORTS + " : operation " + option);
					exceptionService.executeExceptionWithSupports(t);
				} else if (option == Propagation.NOT_SUPPORTED) {
					System.err.println("Propagation " + Propagation.NOT_SUPPORTED + " : operation " + option);
					exceptionService.executeExceptionWithNotSupported(t);
				} 
				else if (option == null) {
					System.err.println("Propagation INTERNAL_" + Propagation.REQUIRES_NEW + " : operation " + option);
					this.executeException(t); 
				} 
			} catch (Exception e) {
				System.err.println(option + " : " + e.getClass());
			}
		}
		return Optional.ofNullable(savedMember);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T extends Exception> void executeException(T exception) throws T {
		throw exception;
	}
}
