package com.lm.pedro.test.receipt.model.repositories;

import com.lm.pedro.test.receipt.model.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByActiveTrue();
}
