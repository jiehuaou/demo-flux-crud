package com.example.crud.demo.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  // include super
public class Box extends Rectangle {

    private double height;
}