package klondike.model.hw02;

import java.util.Objects;

/**
 * Represents a basic card that has no additional fields associated with it.
 */
public class BasicCard implements PlayingCard {
  private final Rank num;
  private final Suit suit;

  /**
   * Constructor to make a BasicCard.
   *
   * @param num  represents the rank of the card
   * @param suit represents the type using one of the following (♣, ♠, ♡, ♢)
   * @throws IllegalArgumentException when an enum is null
   */
  public BasicCard(Rank num, Suit suit) throws IllegalArgumentException {
    if (num == null || suit == null) {
      throw new IllegalArgumentException("Card properties must not be null");
    }
    this.num = num;
    this.suit = suit;
  }

  /**
   * A copy constructor to avoid aliasing and mutating a given deck.
   *
   * @param toCopy the card to be copied
   */
  public BasicCard(BasicCard toCopy) {
    this(toCopy.getRank(), toCopy.getSuit());
  }

  /**
   * Returns the suit of the card for the purposes of move validation.
   *
   * @return the suit of the card
   */
  public Suit getSuit() {
    return this.suit;
  }

  /**
   * Returns the rank of the card for the purposes of move validation.
   *
   * @return the rank (value) of the card
   */
  public Rank getRank() {
    return this.num;
  }

  /**
   * Determines if the given Rank matches the Rank of this card.
   *
   * @param compare the Rank to match to
   * @return true if equal, false if not
   */
  public boolean isRank(Rank compare) {
    return this.getRank() == compare;
  }

  /**
   * Determines the numeric value of the card.
   *
   * @return the number the card is worth
   */
  @Override
  public int getValue() {
    return this.getRank().getValue();
  }

  /**
   * Renders a card with its value followed by its suit as one of
   * the following symbols (♣, ♠, ♡, ♢).
   * For example, the 3 of Hearts is rendered as {@code "3♡"}.
   *
   * @return the formatted card
   */
  @Override
  public String toString() {
    return this.num.toString() + this.suit.toString();
  }

  /**
   * Hashes a card's information, to use in determining equality.
   * Overrides the base hashcode()
   *
   * @return the hashed card
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.num, this.suit);
  }

  /**
   * Checks the equality between cards, without requiring the other card to be the same
   * implementation type. Overrides the base equals() method.
   *
   * @param other the reference object with which to compare.
   * @return true if equal, false if unequal
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Card)) {
      return false;
    }
    Card that = (Card) other;
    return this.toString().equals(that.toString());
  }
}
