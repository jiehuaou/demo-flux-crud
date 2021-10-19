package com.example.crud.demo.logic;

import lombok.extern.log4j.Log4j2;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.*;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

@Log4j2
public class ValidationAdvice implements MethodBeforeAdvice {
    static private ExecutableValidator executableValidator;
    static {
        // Using the javax validation factory mostly works, but doesn't capture the parameter names
        // ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // LocalValidatorFactoryBean uses DefaultParameterNameDiscoverer which does get the param names

        // localFactory();
        anotherFactory();
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.info("test method ===> {}", method);
        Set<ConstraintViolation<Object>> violations = executableValidator.validateParameters(target, method, args);
        if (!violations.isEmpty()) {
            ConstraintViolation [] v1 = new ConstraintViolation[violations.size()];
            v1 = violations.toArray(v1);
            log.warn("violations --> {}", v1[0].getMessage());
            throw new ConstraintViolationException(violations);
        }
    }

    static void localFactory(){
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.afterPropertiesSet();
        executableValidator = factory.getValidator().forExecutables();
        factory.close();
    }

    static void anotherFactory(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator va = factory.getValidator();
        executableValidator = va.forExecutables();
        factory.close();
    }
}
