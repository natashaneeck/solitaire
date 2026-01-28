package klondike.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that uses two ArrayLists to keep track of the Cards in this pile and which are
 * visible.
 */
public abstract class AbstractPile<C extends Card> implements Pile<C> {
  private List<C> cards;
  private List<Boolean> visibilityTracker;

  /**
   * Creates an AbstractPile with a list of cards, and initializes the visibilityTracker to the
   * same length with each Card assumed to be starting as face-up.
   *
   * @param cards the cards in the Pile
   */
  public AbstractPile(ArrayList<C> cards) {
    this.cards = cards;
    this.visibilityTracker = new ArrayList<>();

    for (C card : cards) {
      visibilityTracker.add(true);
    }
  }

  /**
   * Initializes an empty pile.
   */
  public AbstractPile() {
    this.cards = new ArrayList<>();
    this.visibilityTracker = new ArrayList<>();
  }

  /**
   * Returns a copy of the list of Cards in this Pile.
   *
   * @return the list of Cards
   */
  public List<C> getList() {
    return new ArrayList<>(this.cards);
  }

  /**
   * Counts the number of visible cards in the Pile.
   *
   * @return the count
   */
  public int countVisibleCards() {
    int count = 0;
    for (boolean value : this.visibilityTracker) {
      if (value) {
        count++;
      }
    }
    return count;
  }

  /**
   * Checks whether the specified card in the pile is visible. Assumes 0-indexing.
   *
   * @param cardIndex the index of the card in the pile, starting from 0 at the top.
   * @return whether it is visible
   */
  public boolean isCardVisible(int cardIndex) {
    return this.visibilityTracker.get(cardIndex);
  }

  /**
   * Determines how many Cards are in this pile.
   *
   * @return the number of cards
   */
  public int getPileSize() {
    return this.cards.size();
  }

  /**
   * Adds a Card to the end of the Pile, assumes it is NOT visible.
   *
   * @param card the card to add
   */
  public void buildPile(C card) {
    this.cards.add(card);
    this.visibilityTracker.add(false);
  }

  /**
   * Adds a Card to the end of the Pile, assumes it is visible.
   *
   * @param card the card to add
   */
  public void addCard(C card) {
    this.cards.add(card);
    this.visibilityTracker.add(true);
  }

  /**
   * Removes the last instance of a specified Card. Also removes the matching visibility
   * value in the tracker list.
   *
   * @param card the card to remove
   */
  public C removeCard(C card) {
    int lastIndex = this.cards.lastIndexOf(card);
    this.visibilityTracker.remove(lastIndex);
    return this.cards.remove(lastIndex);
  }

  /**
   * Removes the Card from the given index.
   *
   * @param toRemove the index to remove the Card at
   */
  public C removeIndex(int toRemove) {
    this.visibilityTracker.remove(toRemove);
    return this.cards.remove(toRemove);
  }

  /**
   * Makes the last Card in a Pile face-up, whether or not it already was.
   */
  public void revealLast() {
    this.revealIndex(this.cards.size() - 1);
  }

  /**
   * Adds all the cards to this Pile in the given order, updating the visibilityTracker to
   * consider the new cards all visible.
   *
   * @param cards the cards to add
   */
  public void addAll(List<C> cards) {
    this.cards.addAll(cards);
    for (C card : cards) {
      this.visibilityTracker.add(true);
    }
  }

  /**
   * Removes the last instance of each Card in this Pile that matches each Card in the given list.
   * Also removes the matching values in the visibilityTracker.
   *
   * @param cards the cards to remove
   */
  public void removeAll(List<C> cards) {
    for (C card : cards) {
      int lastIndex = this.cards.lastIndexOf(card);
      if (lastIndex >= 0) {
        this.cards.remove(lastIndex);
        this.visibilityTracker.remove(lastIndex);
      }
    }
  }

  /**
   * Reveals the card at the given index.
   *
   * @param index the index to reveal a card at
   */
  public void revealIndex(int index) {
    this.visibilityTracker.set(index, true);
  }
}
