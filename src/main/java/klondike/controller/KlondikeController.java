package klondike.controller;

import java.util.List;
import klondike.model.hw02.Card;
import klondike.model.hw02.KlondikeModel;

/**
 * The interface representing the methods for a controller of a Klondike game.
 */
public interface KlondikeController {


  /**
   * Plays a new game of Klondike using the provided model.
   *
   * @param model the model being used to play the game
   * @param deck the deck of cards being dealt
   * @param shuffle whether the deck should be shuffled when dealt
   * @param numRows the number of columns/piles the game uses
   * @param numDraw the number of draw cards visible at a time
   * @throws IllegalArgumentException if the provided model is null
   * @throws IllegalStateException when the controller input/output malfunctions
   */
  <C extends Card> void playGame(KlondikeModel<C> model, List<C> deck,
                                 boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException, IllegalStateException;
}
