package klondike.model.hw02;

/**
 * Represents a type of Card that is used for playing our version of Klondike.
 */
public interface PlayingCard extends Card {

  /**
   * Returns the suit of the card for the purposes of move validation.
   *
   * @return the suit of the card
   */
  public Suit getSuit();

  /**
   * Returns the rank of the card for the purposes of move validation.
   *
   * @return the rank (value) of the card
   */
  public Rank getRank();

  /**
   * Determines the numeric value of the card.
   *
   * @return the number the card is worth
   */
  public int getValue();

  /**
   * Determines if the given Rank matches the Rank of this card.
   *
   * @param compare the Rank to match to
   * @return true if equal, false if not
   */
  public boolean isRank(Rank compare);
}
