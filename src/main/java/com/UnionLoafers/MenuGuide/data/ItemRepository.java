package com.UnionLoafers.MenuGuide.data;

import com.UnionLoafers.MenuGuide.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
  List<Item> findItemsByNameContainingIgnoreCaseOrDescContainingIgnoreCase(String name, String desc);
}
