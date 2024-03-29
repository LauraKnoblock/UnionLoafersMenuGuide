package com.UnionLoafers.MenuGuide.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Item extends AbstractEntity {

  @NotBlank(message = "Name is required")
  @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
  private String name;
  @Column(name = "description")
  @Size(max = 500, message = "Description is too long!")
  private String desc;

  @ManyToOne
  @NotNull(message = "Category is required")
  private ItemCategory itemCategory;

  @Column(name="image")
  private String image;

  @Column(name="ingredients")
  private String ingredients;

  @Column(name="vegan", length=255)
  private String vegan;

  public Item(String name, String desc, ItemCategory itemCategory, String image, String ingredients, String vegan) {
    this.name = name;
    this.desc = desc;
    this.itemCategory = itemCategory;
    this.image = image;
    this.ingredients = ingredients;
    this.vegan = vegan;
  }

  public Item() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getIngredients() {
    return ingredients;
  }

  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
  }

  public String getVegan() {
    return vegan;
  }

  public void setVegan(String vegan) {
    this.vegan = vegan;
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

  public ItemCategory getItemCategory() {
    return itemCategory;
  }

  public void setItemCategory(ItemCategory itemCategory) {
    this.itemCategory = itemCategory;
  }
}
