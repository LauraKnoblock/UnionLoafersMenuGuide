package com.UnionLoafers.MenuGuide.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Item extends AbstractEntity {

  @NotBlank(message = "Name is required")
  @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
  private String name;
  @Column(name = "description")
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


  public ItemType getType() {
    return type;
  }

  public void setType(ItemType type) {
    this.type = type;
  }

}
