package klondike.model.hw04;

import klondike.model.hw02.BasicPile;
import klondike.model.hw02.PlayingCard;
import klondike.model.hw02.Suit;

/**
 * A class containing validation methods for the moves and rules of Klondike games that follow
 * the rules of Whitehead Klondike.
 */
public class WhiteheadValidData extends AbstractValidData {
  /**
   * Ensures the cards specified can be added to the end of the specified cascade pile.
   * Any cards can be added to empty piles, not just Kings. All cards moved must be the same Suit.
   *
   * @param srcPile the cascade pile to be added to
   * @param adding  the cards being added
   * @throws IllegalStateException if the move is not allowable
   */
  @Override
  public void validAddToCascade(BasicPile srcPile, BasicPile adding) throws IllegalStateException {
    if (!srcPile.getList().isEmpty()) {
      if (!this.validNext(adding.getList().getFirst(), srcPile.getList().getLast())) {
        throw new IllegalStateException("Invalid card add to pile");
      }
    }
    if (!this.allSameSuit(adding)) {
      throw new IllegalStateException("Invalid pile move, must be all same suit.");
    }
  }

  /**
   * Checks that this top card is allowed to be placed on top of the given bottom card in
   * a cascade pile. Requires builds to be single-colored.
   *
   * @param top    The card being moved onto the pile
   * @param bottom The card that is at the top of the pile being moved onto
   * @return whether it is valid
   */
  @Override
  public boolean validNext(PlayingCard top, PlayingCard bottom) {
    return top.getSuit().sameColor(bottom.getSuit())
        && top.getRank().getValue() == bottom.getRank().getValue() - 1;
  }

  /**
   * Determines if all the Cards in the Pile are the same Suit.
   *
   * @param toCheck the Cards to compare
   * @return whether they are the same Suit
   */
  private boolean allSameSuit(BasicPile toCheck) {
    Suit matchSuit = null;
    boolean same = true;

    for (PlayingCard card : toCheck.getList()) {
      if (matchSuit != null) {
        same = same && card.getSuit() == matchSuit;
      } else {
        matchSuit = card.getSuit();
      }
    }
    return same;
  }
}
