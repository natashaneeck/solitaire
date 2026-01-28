package klondike.model.hw04;

import java.util.List;
import klondike.model.hw02.BasicCard;
import klondike.model.hw02.BasicPile;
import klondike.model.hw02.KlondikeModel;

/**
 * Represents a Whitehead version of the game Klondike, which uses foundation, cascade, and
 * drawCards piles. The numbers of these piles and the contents of the decks can vary, but the
 * deck must consist of valid runs only, which are equal sizes starting from an Ace. The number of
 * foundation piles equals the number of aces in the deck.
 * Whitehead rules require valid builds to be the same color, moving piles to be the same suit,
 * all cards are face-up, and any card value can be moved to an empty cascade pile.
 */
public class WhiteheadKlondike extends AbstractKlondike implements KlondikeModel<BasicCard> {

  /**
   * Initializes the game into a state that is ready for someone to call startGame and begin
   * playing. Game is not yet playable after just running the constructor.
   */
  public WhiteheadKlondike() {
    super(new WhiteheadValidData());
  }

  /**
   * Deal a new game of Klondike.
   * The cards to be used and their order are specified by the given deck,
   * unless the {@code shuffle} parameter indicates the order should be ignored.
   *
   * <p>This method first verifies that the deck is valid. It deals cards in rows
   * (left-to-right, top-to-bottom) into the characteristic cascade shape
   * with the specified number of rows, followed by (at most) the specified number of
   * drawCards cards. When {@code shuffle} is {@code false}, the {@code deck}
   * must be used in order and
   * the 0th card in {@code deck} is used as the first card dealt.
   * There will be as many foundation piles as there are Aces in the deck.</p>
   *
   * <p>A valid deck must consist cards that can be grouped into equal-length,
   * consecutive runs of cards (each one starting at an Ace, and each of a single suit).</p>
   *
   * <p>This method should have no side effects other than configuring this model instance,
   * and should work for any valid arguments.</p>
   *
   * @param deck     the deck to be dealt
   * @param shuffle  if {@code false}, use the order as given by {@code deck},
   *                 otherwise use a randomly shuffled order
   * @param numPiles number of piles to be dealt
   * @param numDraw  maximum number of drawCards cards available at a time
   * @throws IllegalStateException    if the game has already started
   * @throws IllegalArgumentException if the deck is null or invalid,
   *                                  a full cascade cannot be dealt with the given sizes,
   *                                  or another input is invalid
   */
  @Override
  public void startGame(List<BasicCard> deck, boolean shuffle, int numPiles, int numDraw) {
    super.startGame(deck, shuffle, numPiles, numDraw);

    for (BasicPile pile : this.cascades) {
      for (int index = 0; index < pile.getPileSize(); index++) {
        pile.revealIndex(index);
      }
    }
  }
}