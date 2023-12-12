package com.UnionLoafers.MenuGuide.models;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.util.Objects;
@Entity
public class ItemCategory {

  @Id
  @GeneratedValue
  private int id;

  @Size(min =3, message="Name must be at least 3 characters long")
  private String name;
  public ItemCategory(@Size(min = 3, message = "Name must be at least 3 characters long") String name) {
    this.name = name;
  }

  public ItemCategory() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }
  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemCategory that = (ItemCategory) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}