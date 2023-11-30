package com.UnionLoafers.MenuGuide.models;

public enum ItemType {

  SANDWICH("Sandwich"),
  PIZZA("Pizza"),
  SOUP("Soup"),
  SALAD("Salad");

  private final String displayName;

  ItemType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
