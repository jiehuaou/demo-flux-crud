package com.example.crud.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.framework.ProxyFactory;

import com.example.crud.demo.logic.MyValidator;
import com.example.crud.demo.logic.ValidationAdvice;
import com.example.crud.demo.model.CatalogueItem;

import lombok.extern.log4j.Log4j2;

//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
//@Import(MyConfig.class)
@ExtendWith(MockitoExtension.class)
@Log4j2
public class MyValidatorTests {

    MyValidator testValidator;

    @BeforeEach
    public void initializeTest() {
        // No validation wrapper used.
        ProxyFactory factory = new ProxyFactory(new MyValidator());
        factory.addAdvice(new ValidationAdvice());
        testValidator = (MyValidator) factory.getProxy();
    }


    @Test
    public void testInvalidSku(){

        Exception ex1 = assertThrows(javax.validation.ConstraintViolationException.class, ()->{
            CatalogueItem c3 = new CatalogueItem(1L, "ww", "abc", "aaa@163.com",
                    "cat", 10.0, 5, Instant.ofEpochSecond(10L), null);
            testValidator.validate(c3);
        });
        Assertions.assertTrue(ex1.getMessage().contains("sku size must be 3~50"));

    }

//    @Test
//    public void testInvalidEmail(){
//        Exception ex2 = assertThrows(javax.validation.ConstraintViolationException.class, ()->{
//            CatalogueItem c3 = new CatalogueItem(2L, "www", "abc", "aaa-163.com");
//            testValidator.validate(c3);
//        });
//        Assertions.assertTrue(ex2.getMessage().contains("please input email format"));
//    }

    @Test
    public void testValid(){
        CatalogueItem c3 = new CatalogueItem(1L, "wwwwwwwwwwww", "abc", "aaa@163.com",
                "cat", 10.0, 5, Instant.ofEpochSecond(10L), null);
        String result = testValidator.validate(c3);
        assertTrue(result.equals("Valid"));
    }

    @Test
    public void testValidator(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Exception ex1 = assertThrows(javax.validation.ConstraintViolationException.class, ()-> {
            CatalogueItem c3 = new CatalogueItem(1L, "ww", "abc", "aaa@163.com",
                    "cat", 10.0, 5, Instant.ofEpochSecond(10L), null);
            Set<ConstraintViolation<Object>> err = validator.validate(c3);
            if (!err.isEmpty()) {
                ConstraintViolation[] v1 = new ConstraintViolation[err.size()];
                v1 = err.toArray(v1);
                log.warn("violations --> {}", v1[0].getMessage());
                throw new ConstraintViolationException(err);
            }
        });

        Assertions.assertTrue(ex1.getMessage().contains("sku size must be 3~50"));
    }
}
