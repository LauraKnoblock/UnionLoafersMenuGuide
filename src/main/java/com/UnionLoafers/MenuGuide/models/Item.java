package com.UnionLoafers.MenuGuide.models;
import java.util.Objects;

public class Item {

  private int id;
  private static int nextId = 1;

  private String name;
  private String desc;
  private ItemType type;

  public Item(String name, String desc, ItemType type) {
    this.name = name;
    this.desc = desc;
    this.id = nextId;
    this.type = type;
    nextId++;
  }

  public Item() {
    this.id = nextId;
    nextId++;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public String toString() {
    return name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public int getId() {
    return id;
  }

  public ItemType getType() {
    return type;
  }

  public void setType(ItemType type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return id == item.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
