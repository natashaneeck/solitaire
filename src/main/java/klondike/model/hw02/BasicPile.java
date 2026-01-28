package klondike.model.hw02;

import java.util.ArrayList;

/**
 * An extension of AbstractPile which specifically uses BasicCards.
 */
public class BasicPile extends AbstractPile<BasicCard> {

  /**
   * Creates an AbstractPile with a list of cards, and initializes the visibilityTracker to the
   * same length with each Card assumed to be starting as face-up.
   *
   * @param cards the cards in the Pile
   */
  public BasicPile(ArrayList<BasicCard> cards) {
    super(cards);
  }

  /**
   * Initializes an empty BasicPile.
   */
  public BasicPile() {
    super();
  }
}
