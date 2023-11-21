package com.UnionLoafers.MenuGuide.data;

import com.UnionLoafers.MenuGuide.models.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ItemData {

  // need a place to put events
  private static final Map<Integer, Item> items = new HashMap<>();

  // get all events
  public static Collection<Item> getAll() {
    return items.values();
  }

  // get a single event
  public static Item getById(int id) {
    return items.get(id);
  }
  // add an event

  public static void add(Item item) {
    items.put(item.getId(), item);
  }

  // remove an event
public static void remove(int id) {
    items.remove(id);
}
}
