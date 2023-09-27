package com.example.crud.demo;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import com.example.crud.demo.model.Box;
import com.example.crud.demo.model.CatalogueItem;
import com.example.crud.demo.model.Rectangle;

@ExtendWith(SpringExtension.class)
public class PojoTests {
    @Test
    public void testCatalogueItem(){
        CatalogueItem c1 = new CatalogueItem(null, "www", "abc", "aaa@163.com", "cat", 10.0, 5, Instant.ofEpochSecond(10L), null); //CatalogueItem(1L, "www", "abc");
        CatalogueItem c2 = new CatalogueItem(null, "www", "abc", "aaa@163.com", "cat", 10.0, 5, Instant.ofEpochSecond(10L), null);
        CatalogueItem c3 = new CatalogueItem(1L, "www", "abc", "aaa@163.com", "cat", 10.0, 5, Instant.ofEpochSecond(10L), null);
        Assert.isTrue(c1.equals(c2), "c1 == c2");
        Assert.isTrue(!c2.equals(c3), "c2 != c3");
    }

    @Test
    public void testBox1(){
        Box b1 = new Box(10.0);
        Box b2 = new Box(10.0);
        Box b3 = new Box(11.0);
        String b4 = "10.0";
        String b5 = null;

        Rectangle r1 = new Box(10.0);
        Rectangle r2 = r1;
        Rectangle r3 = new Rectangle();

        b1.equals(b2);

        System.out.println("b1 == b2 => "+(b1 == b2));
        System.out.println("b1 == b3 => "+(b1 == b3));
        System.out.println("b1 == r1 => "+(b1 == r1));
        System.out.println("r1 == r2 => "+(r1 == r2));

        System.out.println("b1.equals(b2) => "+b1.equals(b2));
        System.out.println("b1.equals(b3) => "+b1.equals(b3));
        System.out.println("b1.equals(b4) => "+b1.equals(b4));
        System.out.println("b1.equals(b5) => "+b1.equals(b5));
        System.out.println("b1.equals(r1) => "+b1.equals(r1));
        System.out.println("b1.equals(r2) => "+b1.equals(r2));

        System.out.println("b1.equals(r1) => "+b1.equals(r3));
    }


}
