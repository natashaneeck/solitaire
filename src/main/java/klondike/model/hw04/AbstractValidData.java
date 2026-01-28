package klondike.model.hw04;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;
import klondike.model.hw02.BasicCard;
import klondike.model.hw02.BasicPile;
import klondike.model.hw02.PlayingCard;
import klondike.model.hw02.Rank;
import klondike.model.hw02.ValidData;

/**
 * An abstract class containing validation methods for various moves and rules of Klondike
 * implementations.
 */
public abstract class AbstractValidData implements ValidData<BasicCard> {

  /**
   * Ensures the given deck consists of equal-length single-suit runs of consecutive values
   * starting from Ace, with as many runs as wanted.
   *
   * @param deck the deck to be validated and checked
   * @return true if the deck is valid, and false if invalid
   */
  @Override
  public boolean validateDeck(List<BasicCard> deck) {
    int maxRank = 0;

    if (deck == null || deck.isEmpty()) {
      return false;
    }

    for (BasicCard card : deck) {
      if (card == null) {
        return false;
      }
      maxRank = max(maxRank, card.getValue());
    }

    int numAces = this.numAces(deck);

    return (deck.size() <= numAces * 13 && maxRank == (deck.size() / numAces));
  }

  /**
   * Counts the number of aces in a given deck.
   *
   * @param deck the deck to check for the ace cards
   * @return an integer count of the number of cards whose rank is Ace
   */
  @Override
  public int numAces(List<BasicCard> deck) {
    int aceCount = 0;
    for (BasicCard card : deck) {
      if (card != null && card.isRank(Rank.ACE)) {
        aceCount++;
      }
    }
    return aceCount;
  }

  /**
   * Checks that adding the given card to the given foundation pile is valid. Meaning, if the
   * pile is empty, the player can only add an Ace. If non-empty, the given card must be the same
   * suit and sequential numerical value.
   *
   * @param adding the card to be added to the foundation pile
   * @param pile   the specific foundation pile being added to
   * @throws IllegalStateException if the move is not allowable
   */
  @Override
  public void validAddToFoundation(PlayingCard adding, ArrayList<BasicCard> pile)
      throws IllegalStateException {

    if (pile.isEmpty()) {
      if (!adding.isRank(Rank.ACE)) {
        throw new IllegalStateException("Must move an Ace card to empty foundation pile");
      }
    } else {
      if (!this.validCardForFoundation(adding, pile.getLast())) {
        throw new IllegalStateException("Cannot add this card to foundation pile");
      }
    }
  }

  /**
   * Checks that adding this card on top of the given last card in a foundation pile is valid.
   * The given card must be the same suit and sequential numerical value.
   *
   * @param last the last card in the foundation pile being added to
   * @return whether it is valid
   */
  @Override
  public boolean validCardForFoundation(PlayingCard top, PlayingCard last) {
    return top.getSuit() == last.getSuit() && top.getValue() == last.getValue() + 1;
  }

  /**
   * Ensures the given index is valid.
   *
   * @param index    the index to check the validity of
   * @param numPiles the total number of piles
   * @return whether that index is between 0 and numPiles - 1
   */
  @Override
  public boolean validPileIndex(int index, int numPiles) {
    return index >= 0 && index < numPiles;
  }

  /**
   * Ensures the card specified can be added to the end of the specified cascade pile.
   * The specific rules of this are determined by implementation.
   *
   * @param srcPile the cascade pile to be added to
   * @param adding  the cards being added
   * @throws IllegalStateException if the move is not allowable
   */

  public abstract void validAddToCascade(BasicPile srcPile, BasicPile adding)
      throws IllegalStateException;

  /**
   * Checks that this top card is allowed to be placed on top of the given bottom card in
   * a cascade pile. The requirements for that are determined by implementation.
   *
   * @param top    The card being moved onto the pile
   * @param bottom The card that is at the top of the pile being moved onto
   * @return whether it is valid
   */
  @Override
  public abstract boolean validNext(PlayingCard top, PlayingCard bottom);

  /**
   * Determines whether the pile specified by the following parameters is a legal Klondike move,
   * as determined by implementation.
   *
   * @param srcPile  the index of the pile being moved from
   * @param numCards the number of cards being moved from srcPile
   * @param destPile the index of the pile being moved to
   * @param numPiles the number of cascade piles
   * @param visCount the number of visible cards in the srcPile
   * @return whether it is a valid move
   */
  @Override
  public boolean validPileMove(int srcPile, int numCards, int destPile, int numPiles,
                               int visCount) {
    return (srcPile != destPile) && (srcPile < numPiles) && srcPile >= 0 && numCards > 0
        && (destPile < numPiles) && (numCards <= visCount) && destPile >= 0;
  }
}
