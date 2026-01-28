package klondike.model.hw02;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface for checking that data is valid and follows expected rules for a KlondikeModel.
 */
public interface ValidData<C extends Card> {


  /**
   * Ensures the given deck consists of equal-length single-suit runs of consecutive values
   * starting from Ace, with as many runs as wanted.
   *
   * @param deck the deck to be validated and checked
   * @return true if the deck is valid, and false if invalid
   */
  public boolean validateDeck(List<BasicCard> deck);

  /**
   * Counts the number of aces in a given deck.
   *
   * @param deck the deck to check for the ace cards
   * @return an integer count of the number of cards whose rank is Ace
   */
  public int numAces(List<BasicCard> deck);

  /**
   * Ensures the cards specified can be added to the end of the specified cascade pile.
   * Implementation decides the rules of what is a valid build and pile move.
   *
   * @param srcPile   the cascade pile to be added to
   * @param adding the cards being added
   * @throws IllegalStateException if the move is not allowable
   */
  public void validAddToCascade(BasicPile srcPile, BasicPile adding)
      throws IllegalStateException;

  /**
   * Checks that adding the given card to the given foundation pile is valid. Meaning, if the
   * pile is empty, the player can only add an Ace. If non-empty, the given card must be the same
   * suit and sequential numerical value.
   *
   * @param adding the card to be added to the foundation pile
   * @param pile   the specific foundation pile being added to
   * @throws IllegalStateException if the move is not allowable
   */
  public void validAddToFoundation(PlayingCard adding, ArrayList<BasicCard> pile)
      throws IllegalStateException;

  /**
   * Checks that adding this card on top of the given last card in a foundation pile is valid.
   * The given card must be the same suit and sequential numerical value.
   *
   * @param last the last card in the foundation pile being added to
   * @return whether it is valid
   */
  public boolean validCardForFoundation(PlayingCard top, PlayingCard last);

  /**
   * Checks that this top card is allowed to be placed on top of the given bottom card in
   * a cascade pile.
   *
   * @param top The card being moved onto the pile
   * @param bottom The card that is at the top of the pile being moved onto
   * @return whether it is valid
   */
  public boolean validNext(PlayingCard top, PlayingCard bottom);

  /**
   * Determines whether the pile specified by the source parameters can legally be moved onto
   * the specified pile.
   *
   * @param srcPile the index of the pile being moved from
   * @param numCards the number of cards being moved from srcPile
   * @param destPile the index of the pile being moved to
   * @param numPiles the number of cascade piles
   * @param visCount the number of visible cards in the srcPile
   * @return whether it is a valid move
   */
  public boolean validPileMove(int srcPile, int numCards, int destPile, int numPiles,
                               int visCount);

  /**
   * Ensures the given index is valid.
   *
   * @param index the index to check the validity of
   * @param numPiles the total number of piles
   * @return whether that index is between 0 and numPiles - 1
   */
  public boolean validPileIndex(int index, int numPiles);
}
