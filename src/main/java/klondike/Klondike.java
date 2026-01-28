package klondike;

import java.io.InputStreamReader;
import klondike.controller.KlondikeController;
import klondike.controller.KlondikeTextualController;
import klondike.model.hw02.KlondikeModel;
import klondike.model.hw04.KlondikeCreator;

/**
 * The main method for running a game of Klondike. Allows the user to determine the version of
 * Klondike they would like to play. If unspecified, there are 7 cascade piles and 3 draw cards.
 */
public final class Klondike {

  /**
   * Takes in command-line arguments determining the game type of Klondike, and runs the game.
   *
   * @param args the user input
   */
  public static void main(String[] args) {
    KlondikeModel model;
    int numPiles = 7;
    int numDraw = 3;
    if (args.length > 0) {
      model = KlondikeCreator.create(KlondikeCreator.GameType.valueOf(args[0].toUpperCase()));
      if (args.length > 1) {
        numPiles = Klondike.getPiles(args[1]);
      }
      if (args.length > 2) {
        numDraw = Klondike.getDraw(args[2]);
      }

      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      KlondikeController control = new KlondikeTextualController(input, output);
      control.playGame(model, model.createNewDeck(), false, numPiles, numDraw);
    } else {
      throw new IllegalArgumentException("No arguments given");
    }
  }


  private static int getPiles(String input) {
    try {
      if (input != null && Integer.parseInt(input) > 0) {
        return Integer.parseInt(input);
      } else {
        return 7;
      }
    } catch (NumberFormatException e) {
      return 7;
    }
  }

  private static int getDraw(String input) {
    try {
      if (input != null && Integer.parseInt(input) >= 0) {
        return Integer.parseInt(input);
      } else {
        return 3;
      }
    } catch (NumberFormatException e) {
      return 3;
    }
  }
}