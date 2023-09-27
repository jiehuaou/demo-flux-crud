package com.example.crud.demo.rest;

import com.example.crud.demo.service.CatalogueCrudService;
import com.example.crud.demo.model.CatalogueItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;
import java.util.Date;

@RestController
public class CatalogueController {

    @Autowired
    CatalogueCrudService catalogueCrudService;

    @GetMapping(path = "/")
    public String home() {
        return "Hello World " + new Date().toString() + " \n";
    }


    @GetMapping(path= "/item-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<CatalogueItem> getCatalogueItemsStream() {
        return catalogueCrudService
                .getCatalogueItems()
                .delayElements(Duration.ofMillis(200))
                .takeWhile(x->(x.getId()<10L));
    }

    @GetMapping("/item/{sku}")
    public Mono<CatalogueItem> getCatalogueItemBySKU(@PathVariable(value = "sku") String skuNumber)
            throws ResourceNotFoundException {
        // todo
        return catalogueCrudService.getCatalogueItemBySku(skuNumber);
    }

    @PostMapping("/item-x")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<ResponseEntity> addCatalogueItem(@Valid @RequestBody CatalogueItem catalogueItem) {
        Mono<Long> id = catalogueCrudService.addCatalogItem(catalogueItem);

        return id.map(value -> ResponseEntity.status(HttpStatus.CREATED).body(value));
    }

    @PutMapping("/item-x/{sku}")
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<String> updateCatalogueItem(
            @PathVariable(value = "sku") String skuNumber,
            @Valid @RequestBody CatalogueItem catalogueItem) throws ResourceNotFoundException {

        return catalogueCrudService.updateCatalogueItem(catalogueItem);
    }

    @DeleteMapping("/item-x/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<Long> deleteCatalogueItem(
            @PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return catalogueCrudService.delete(id);
    }

}
