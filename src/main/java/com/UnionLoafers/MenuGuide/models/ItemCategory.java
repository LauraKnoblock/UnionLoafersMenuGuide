package com.UnionLoafers.MenuGuide.models;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
public class ItemCategory extends AbstractEntity {


  @Size(min = 3, message = "Name must be at least 3 characters long")
  private String name;

  @OneToMany(mappedBy = "itemCategory")
  private final List<Item> items = new ArrayList<>();

  public ItemCategory(@Size(min = 3, message = "Name must be at least 3 characters long") String name) {
    this.name = name;
  }

  public ItemCategory() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Item> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return name;
  }

}