package klondike.model.hw02;

/**
 * This enumerated type represents the different options for card suits, along with their
 * char representation.
 */
public enum Suit {
  CLUBS('♣', "Black"), DIAMONDS('♢', "Red"),
  HEARTS('♡', "Red"), SPADES('♠', "Black");

  private final char symbol;
  private final String color;

  private Suit(char symbol, String color) {
    this.symbol = symbol;
    this.color = color;
  }

  @Override
  public String toString() {
    return String.valueOf(symbol);
  }

  /**
   * Returns the color of the card suit.
   *
   * @return "Red" or "Black" depending on which suit it is
   */
  public String getColor() {
    return this.color;
  }

  /**
   * Determines whether two suits have the same color.
   *
   * @param other represents the suit being compared to
   * @return true if same color, or false if different
   */
  public boolean sameColor(Suit other) {
    return this.getColor().equals(other.getColor());
  }
}
