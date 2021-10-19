package com.example.crud.demo.logic;

import com.example.crud.demo.model.CatalogueItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

//import javax.validation.Valid;

@Service
@Validated
public class MyValidator {
    public String validate(@Valid CatalogueItem c){
        return "Valid";
    }
}
