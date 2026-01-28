package klondike.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import klondike.model.hw02.Card;
import klondike.model.hw02.KlondikeModel;
import klondike.view.KlondikeTextualView;
import klondike.view.TextualView;

/**
 * The controller for a Klondike game.
 */
public class KlondikeTextualController implements KlondikeController {
  Readable rd;
  Appendable ap;
  Scanner scan;


  /**
   * Creates a controller object for interacting with the game.
   *
   * @param rd represents the input
   * @param ap represents the output
   * @throws IllegalArgumentException if either argument is null
   */
  public KlondikeTextualController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    this.rd = rd;
    this.ap = ap;
    this.scan = new Scanner(this.rd);
  }


  /**
   * Plays a new game of Klondike using the provided model.
   *
   * @param model   the model being used to play the game
   * @param deck    the deck of cards being dealt
   * @param shuffle whether the deck should be shuffled when dealt
   * @param numRows the number of columns/piles the game uses
   * @param numDraw the number of draw cards visible at a time
   * @throws IllegalArgumentException if the provided model is null
   * @throws IllegalStateException    when the controller input/output malfunctions
   */
  @Override
  public <C extends Card> void playGame(KlondikeModel<C> model, List<C> deck, boolean shuffle,
                                        int numRows, int numDraw)
      throws IllegalArgumentException, IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    TextualView view = new KlondikeTextualView(model, this.ap);

    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException ex) {
      throw new IllegalStateException("Cannot start game");
    }

    try {
      while (!model.isGameOver()) {
        this.transmitGameState(view);
        this.transmit(this.getScoreString(model));
        String cmd = this.nextValidCmd();
        this.processCommand(model, cmd);
      }
      this.transmitGameState(view);
      if (this.hasWon(model, deck)) {
        this.transmit("You win!");
      } else {
        this.transmit("Game over. " + this.getScoreString(model));
      }
    } catch (QuitException ex) {
      this.gameQuitSequence(model, view);
    } catch (IllegalStateException | IllegalArgumentException ex) {
      this.transmit("Invalid move. Play again. " + ex.getMessage());
    } catch (OutOfInputException ex) {
      throw new IllegalStateException("No more input to read");
    }
  }

  /**
   * Determines if the game has been won, which is defined as all cards being moved to foundation
   * piles.
   *
   * @return whether it is won
   */
  private <C extends Card> boolean hasWon(KlondikeModel<C> model, List<C> deck) {
    return model.getScore() == deck.size();
  }

  /**
   * Processes the user's command and tells the model to follow that action.
   * Indices are adjusted from starting at 1 to starting at 0.
   *
   * @param cmd the command the user wants the model to follow
   * @throws QuitException if an input is a "q" or "Q"
   */
  private <C extends Card> void processCommand(KlondikeModel<C> model, String cmd)
      throws QuitException, OutOfInputException {
    try {
      switch (cmd) {
        case "mpp":
          model.movePile(this.nextValidInput() - 1, this.nextValidInput(),
              this.nextValidInput() - 1);
          break;

        case "md":
          model.moveDraw(this.nextValidInput() - 1);
          break;

        case "mpf":
          model.moveToFoundation(this.nextValidInput() - 1,
              this.nextValidInput() - 1);
          break;

        case "mdf":
          model.moveDrawToFoundation(this.nextValidInput() - 1);
          break;

        case "dd":
          model.discardDraw();
          break;

        default:
          throw new IllegalStateException("Invalid command passed into processCommand");
      }

    } catch (IllegalArgumentException | IllegalStateException ex) {
      this.transmit("Invalid move. Play again. " + ex.getMessage());
      this.processCommand(model, this.nextValidCmd());
    }

  }

  /**
   * Writes the given message to the user.
   *
   * @param message message to send
   * @throws IllegalStateException if I/O fails for any reason
   */
  private void transmit(String message) throws IllegalStateException {
    try {
      this.ap.append(message + "\n");
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Constructs the String that tells the user their current game score.
   *
   * @return a String of the game score, without a \n
   */
  private <C extends Card> String getScoreString(KlondikeModel<C> model) {
    return "Score: " + model.getScore();
  }

  /**
   * Transmits the current model game state to the user as formatted by the TextualView.
   */
  private void transmitGameState(TextualView view) {
    try {
      view.render();
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * The sequence of transmissions for the controller to run when user input is a 'q' or a 'Q' at
   * any point.
   */
  private <C extends Card> void gameQuitSequence(KlondikeModel<C> model, TextualView view) {
    this.transmit("Game quit!");
    this.transmit("State of game when quit:");
    this.transmitGameState(view);
    this.transmit(this.getScoreString(model));
  }

  /**
   * Finds the next valid input in the Scanner by getting the next Integer and checking for quit
   * signals. Skips over bad inputs until a good one is found.
   *
   * @return the next Integer
   * @throws QuitException         if there is a "q" or "Q" in the input
   * @throws IllegalStateException if input runs out
   */
  private int nextValidInput() throws QuitException, IllegalStateException, OutOfInputException {
    if (!this.scan.hasNext()) {
      throw new OutOfInputException();
    }

    if (this.scan.hasNextInt()) {
      return this.scan.nextInt();
    }

    this.checkQuit(this.scan.next());
    return this.nextValidInput();
  }

  /**
   * Finds the next command option in the user's input.
   *
   * @return the next command to relay to the model
   * @throws QuitException if input is "q" or "Q"
   */
  private String nextValidCmd() throws QuitException, OutOfInputException {
    if (!this.scan.hasNext()) {
      throw new OutOfInputException();
    }
    String cmd = this.scan.next();
    this.checkQuit(cmd);

    String[] possibleCmds = {"mpp", "md", "mpf", "mdf", "dd"};
    for (String option : possibleCmds) {
      if (cmd.equals(option)) {
        return cmd;
      }
    }
    this.transmit("Invalid move. Play again. " + "Need valid command prompt");
    return this.nextValidCmd();
  }

  /**
   * Checks if the given input String is a quit signal.
   *
   * @param input the String being checked
   * @throws QuitException if the input is "q" or "Q"
   */
  private void checkQuit(String input) throws QuitException {
    if (input.equalsIgnoreCase("q")) {
      throw new QuitException();
    }
  }

  /**
   * A custom Exception object for signaling when a user attempts to quit the game.
   */
  class QuitException extends Exception {
  }

  /**
   * A custom Exception object for signaling when a Scanner runs out of input to read.
   */
  class OutOfInputException extends Exception {
  }
}
