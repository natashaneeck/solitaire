package klondike.model.hw02;

import java.util.List;

/**
 * Represents an ordered grouping of Cards that can be face-up (visible) or face-down (invisible).
 */
public interface Pile<C extends Card> {

  /**
   * Returns a copy of the list of Cards in this Pile.
   *
   * @return the list of Cards
   */
  public List<C> getList();

  /**
   * Counts the number of visible cards in the Pile.
   *
   * @return the count
   */
  public int countVisibleCards();

  /**
   * Checks whether the specified card in the pile is visible. Assumes 0-indexing.
   *
   * @param cardIndex the index of the card in the pile, starting from 0 at the top.
   * @return whether it is visible
   */
  public boolean isCardVisible(int cardIndex);

  /**
   * Determines how many Cards are in this pile.
   *
   * @return the number of cards
   */
  public int getPileSize();

  /**
   * Adds a Card to the end of the Pile, assumes it is NOT visible.
   *
   * @param card the card to add
   */
  public void buildPile(C card);

  /**
   * Adds a Card to the end of the Pile, assumes it is visible.
   *
   * @param card the card to add
   */
  public void addCard(C card);

  /**
   * Removes the last instance of a specified Card.
   *
   * @param card the card to remove
   */
  public C removeCard(C card);

  /**
   * Removes the Card from the given index.
   *
   * @param toRemove the index to remove the Card at
   */
  public C removeIndex(int toRemove);

  /**
   * Makes the last Card in a Pile face-up, whether or not it already was.
   */
  public void revealLast();

  /**
   * Adds all the cards to this Pile in the given order, assuming they are all visible.
   *
   * @param cards the cards to add
   */
  public void addAll(List<C> cards);

  /**
   * Removes the last instance of each Card in this Pile that matches each Card in the given list.
   *
   * @param cards the cards to remove
   */
  public void removeAll(List<C> cards);

  /**
   * Reveals the card at the given index.
   *
   * @param index the index to reveal a card at
   */
  public void revealIndex(int index);
}
