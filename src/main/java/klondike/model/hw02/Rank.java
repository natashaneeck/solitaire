package klondike.model.hw02;

/**
 * Represents the rank of a card, which is its display and corresponding numerical value.
 */
public enum Rank {
  ACE("A", 1),
  TWO("2", 2),
  THREE("3", 3),
  FOUR("4", 4),
  FIVE("5", 5),
  SIX("6", 6),
  SEVEN("7", 7),
  EIGHT("8", 8),
  NINE("9", 9),
  TEN("10", 10),
  JACK("J", 11),
  QUEEN("Q", 12),
  KING("K", 13);

  private final String display;
  private final int num;

  private Rank(String display, int num) {
    this.display = display;
    this.num = num;
  }

  /**
   * Returns the numerical value of the card.
   *
   * @return the number the rank is associated with
   */
  public int getValue() {   // IS IT OKAY THAT ITS PUBLIC??
    return this.num;
  }

  @Override
  public String toString() {
    return String.valueOf(display);
  }
}
