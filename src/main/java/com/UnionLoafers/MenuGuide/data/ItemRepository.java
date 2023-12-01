package com.UnionLoafers.MenuGuide.data;

import com.UnionLoafers.MenuGuide.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
}
