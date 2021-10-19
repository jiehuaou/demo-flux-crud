package com.example.crud.demo.logic;

import com.example.crud.demo.model.CatalogueItem;
import com.example.crud.demo.repo.CatalogueRepository;
import com.example.crud.demo.rest.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@Log4j2
@Service
public class CatalogueCrudService {

    private final CatalogueRepository catalogueRepository;

    public CatalogueCrudService(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }


    public Mono<Long> addCatalogItem(CatalogueItem catalogueItem) {
        catalogueItem.setCreatedOn(Instant.now());

        return catalogueRepository
                .save(catalogueItem)
//                .delayElement(Duration.ofMillis(2000))
                .doOnSuccess(item -> log.info("created --> {}", item))
//                .doOnError(err->log.info("onError --> {}", err))
                .map(x->x.getId());
                //.flatMap(item -> Mono.just(item.getId()));
    }
    /**
    public void updateCatalogueItem(CatalogueItem catalogueItem) throws ResourceNotFoundException{

        Mono<CatalogueItem> catalogueItemfromDB = getCatalogueItemBySku(catalogueItem.getSku());

        catalogueItemfromDB.subscribe(
                value -> {
                    value.setName(catalogueItem.getName());
                    value.setDescription(catalogueItem.getDescription());
                    value.setPrice(catalogueItem.getPrice());
                    value.setInventory(catalogueItem.getInventory());
                    value.setUpdatedOn(Instant.now());

                    catalogueRepository
                            .save(value)
                            .doOnSuccess(item -> log.info("updated --> {}", item))
                            .subscribe();
                });
    }
     */

    public Mono<String> updateCatalogueItem(CatalogueItem catalogueItem) throws ResourceNotFoundException{

        Mono<CatalogueItem> catalogueItemfromDB = getCatalogueItemBySku(catalogueItem.getSku());

        return catalogueItemfromDB.flatMap(
                value -> {
                    value.setName(catalogueItem.getName());
                    value.setDescription(catalogueItem.getDescription());
                    value.setPrice(catalogueItem.getPrice());
                    value.setInventory(catalogueItem.getInventory());
                    value.setUpdatedOn(Instant.now());

                    return catalogueRepository
                            .save(value)
                            .doOnSuccess(item -> log.info("updated --> {}", item))
                            .thenReturn("updated");
                });
    }

    public Flux<CatalogueItem> getCatalogueItems() {
        return catalogueRepository.findAll();
    }

    public Mono<CatalogueItem> getCatalogueItemBySku(String skuNumber) {
        return catalogueRepository
                .findBySku(skuNumber)
                .doOnNext(x->log.info("loaded item --> {}", x))
                .switchIfEmpty(Mono.defer(() -> {
                            log.warn("Catalogue Item not found --> {}", skuNumber);
                            return Mono.error(new ResourceNotFoundException(
                                        String.format("Catalogue Item not found for the provided SKU :: %s", skuNumber)
                                    )// ResourceNotFoundException
                            ); // Mono.error
                        } // lambda
                    ) // defer()
                ); // switchIfEmpty
    }

    public Mono<Long> delete(Long id) {
        return catalogueRepository
                .deleteById(id)
                .doOnSuccess((x)->log.info("deleted --> {}", id))
                .thenReturn(id);
    }
}
