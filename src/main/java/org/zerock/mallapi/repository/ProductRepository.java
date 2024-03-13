package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mallapi.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
