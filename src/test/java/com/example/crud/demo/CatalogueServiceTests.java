package com.example.crud.demo;

import com.example.crud.demo.service.CatalogueCrudService;
import com.example.crud.demo.model.CatalogueItem;
import com.example.crud.demo.rest.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

@Log4j2

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatalogueServiceTests {
    @Autowired
    CatalogueCrudService crudService;

    @Test
    void testGetItem(){
        Mono<CatalogueItem> itemMono = crudService
                .getCatalogueItemBySku("TLG-SKU-0082");

        StepVerifier
                .create(itemMono)
                .expectNextMatches(x->{
                    return x.getId().longValue()==82L ;
                })
                .verifyComplete();
    }




    @Test
    @Order(1)
    void testCrudItemAdd(){

        log.info("testCrudItemAdd ---> starting ");
        ///
        CatalogueItem item = createItem();
        Mono<Long> id = crudService.addCatalogItem(item);
        //id.concatWith()
        Mono<CatalogueItem> addItem = id.flatMap(x->{
            return crudService.getCatalogueItemBySku("TLG-SKU-abc");
        });

        StepVerifier
                .create(addItem)
                .expectNextMatches(x1->{
                    return x1.getName().equalsIgnoreCase(item.getName()) ;
                })
                .verifyComplete();
    }

    @Test
    @Order(2)
    void testCrudItemUpdate() throws ResourceNotFoundException {

        log.info("testCrudItemUpdate ---> starting ");

        CatalogueItem item = createItem();
        item.setName("abc-update");

        Mono<CatalogueItem> updateItem = crudService.updateCatalogueItem(item)
            .flatMap(x1->{
                return crudService.getCatalogueItemBySku("TLG-SKU-abc");
            });


        StepVerifier
                .create(updateItem)
                .expectNextMatches(x1->{
                    return x1.getName().equalsIgnoreCase(item.getName()) ;
                })
                .verifyComplete();
    }

    /**
     * {
     *   "id": null,
     *   "sku": "TLG-SKU-abc",
     *   "name": "abc",
     *   "description": "ITEM DESC abc",
     *   "category": "Books",
     *   "price": 12.0,
     *   "inventory": 10,
     *   "createdOn": "2021-10-14T09:18:39.740270Z",
     *   "updatedOn": null
     * }
     */
    CatalogueItem createItem(){
        CatalogueItem x = CatalogueItem.builder()
                .sku("TLG-SKU-abc")
                .name("abc")
                .description("ITEM DESC abc")
                .category("Books")
                .price(12.0)
                .inventory(10)
                .createdOn(Instant.ofEpochMilli(10))
                .build();

        return x;
    }

    @Test
    @Order(3)
    void testCrudItemDelete(){

        Mono<Long> id = crudService.delete(1001L);

        StepVerifier
                .create(id)
                .expectNextMatches(x1->{
                    return x1.longValue() == 1001L ;
                })
                .verifyComplete();
    }

    @Test
    @Order(4)
    void testCrudItemDeleteCheck(){

        Mono<CatalogueItem> item =
                crudService.getCatalogueItemBySku("TLG-SKU-abc");

        StepVerifier
                .create(item)
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

}
