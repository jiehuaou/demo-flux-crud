package com.example.crud.demo.repo;

import com.example.crud.demo.model.CatalogueItem;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface CatalogueRepository extends ReactiveSortingRepository<CatalogueItem, Long> {
    Mono<CatalogueItem> findBySku(String sku);
}
