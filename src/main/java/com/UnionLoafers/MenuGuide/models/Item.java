package com.UnionLoafers.MenuGuide.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class Item {

  @Id
  @GeneratedValue
  private int id;
  @NotBlank(message = "Name is required")
  @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
  private String name;
  @Column(name="description")
  @Size(max = 500, message = "Description is too long!")
  private String desc;
  private ItemType type;

  public Item(String name, String desc, ItemType type) {
    this.name = name;
    this.desc = desc;
    this.type = type;
  }

  public Item() {
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
