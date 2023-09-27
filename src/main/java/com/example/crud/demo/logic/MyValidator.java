package com.example.crud.demo.logic;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.crud.demo.model.CatalogueItem;

//import javax.validation.Valid;

@Service
@Validated
public class MyValidator {
    public String validate(@Valid CatalogueItem c){
        return "Valid";
    }
}
