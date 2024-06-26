package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.demo.exception.ChildException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    static Exception sharedException;

    @BeforeAll
    static void setUp() {
        sharedException = new RuntimeException();
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
            service.saveWithException(op, sharedException, Propagation.REQUIRES_NEW);
        } catch (Exception e) {
            System.err.println(op.getOperation() + " Exception : " + e.getClass());
        }
        Optional<Operation> foundOperation = repository.findByOperation(op.getOperation());
        String result = foundOperation.isPresent() ? "COMMIT" : "ROLLBACK";

        map.put(op.getOperation(), result);
    }

    @Test
    void REQUIRED() {
        Operation op = Operation.of("REQUIRED");
        try {
            service.saveWithException(op, sharedException, Propagation.REQUIRED);
        } catch (Exception e) {
            System.err.println(op.getOperation() + " Exception : " + e.getClass());
        }
        Optional<Operation> foundOperation = repository.findByOperation(op.getOperation());
        String result = foundOperation.isPresent() ? "COMMIT" : "ROLLBACK";

        map.put(op.getOperation(), result);
    }

    @Test
    void SUPPORTS() {
        Operation op = Operation.of("SUPPORTS");
        try {
            service.saveWithException(op, sharedException, Propagation.SUPPORTS);
        } catch (Exception e) {
            System.err.println(op.getOperation() + " Exception : " + e.getClass());
        }
        Optional<Operation> foundOperation = repository.findByOperation(op.getOperation());
        String result = foundOperation.isPresent() ? "COMMIT" : "ROLLBACK";

        map.put(op.getOperation(), result);

    }

    @Test
    void NOT_SUPPORTED() {
        Operation op = Operation.of("NOT_SUPPORTED");
        try {
            service.saveWithException(op, sharedException, Propagation.NOT_SUPPORTED);
        } catch (Exception e) {
            System.err.println(op.getOperation() + " Exception : " + e.getClass());
        }
        Optional<Operation> foundOperation = repository.findByOperation(op.getOperation());
        String result = foundOperation.isPresent() ? "COMMIT" : "ROLLBACK";

        map.put(op.getOperation(), result);
    }

    @Test
    void ROLLBACK_FOR_CUSTOM_EXCEPTION() {
        Operation op = Operation.of("ROLLBACK_FOR_CUSTOM_EXCEPTION");
        try {
            service.saveWithException(op, new ChildException(), null);
        } catch (Exception e) {
            System.err.println(op.getOperation() + " Exception : " + e.getClass());
        }
        Optional<Operation> foundOperation = repository.findByOperation(op.getOperation());
        String result = foundOperation.isPresent() ? "COMMIT" : "ROLLBACK";

        map.put(op.getOperation(), result);
    }

    @Test
    void INTERNAL_METHOD() {
        Operation op = Operation.of("INTERNAL_METHOD");
        try {
            service.saveWithException(op, sharedException, null);
        } catch (Exception e) {
            System.err.println(op.getOperation() + " Exception : " + e.getClass());
        }
        Optional<Operation> foundOperation = repository.findByOperation(op.getOperation());
        String result = foundOperation.isPresent() ? "COMMIT" : "ROLLBACK";

        map.put(op.getOperation(), result);
    }

}
