package klondike.model.hw02;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;
import klondike.model.hw04.AbstractValidData;

/**
 * A class for checking validity of data for Klondike games that follow the rules of BasicKlondike
 * models.
 */
public class BasicValidData extends AbstractValidData {

  /**
   * Creates the validator object.
   */
  public BasicValidData() {
  }

  /**
   * Ensures the cards specified can be added to the end of the specified cascade pile. Meaning,
   * if the pile is empty then it must be a King, and be the opposite suit with a sequential
   * numerical value if not.
   *
   * @param srcPile the cascade pile to be added to
   * @param adding  the cards being added
   * @throws IllegalStateException if the move is not allowable
   */
  public void validAddToCascade(BasicPile srcPile, BasicPile adding)
      throws IllegalStateException {
    PlayingCard top = adding.getList().getFirst();

    if (srcPile.getList().isEmpty()) {
      if (!top.isRank(Rank.KING)) {
        throw new IllegalStateException("Can only add King to empty cascade pile");
      }
    } else {
      if (!this.validNext(top, srcPile.getList().getLast())) {
        throw new IllegalStateException("Invalid card add to pile");
      }
    }
  }

  /**
   * Checks that this top card is allowed to be placed on top of the given bottom card in
   * a cascade pile. Requires different suit colors.
   *
   * @param top    The card being moved onto the pile
   * @param bottom The card that is at the top of the pile being moved onto
   * @return whether it is valid
   */
  @Override
  public boolean validNext(PlayingCard top, PlayingCard bottom) {
    return !top.getSuit().sameColor(bottom.getSuit())
        && top.getRank().getValue() == bottom.getRank().getValue() - 1;
  }
}
