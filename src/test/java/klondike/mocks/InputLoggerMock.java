package klondike.mocks;

import java.util.List;
import klondike.model.hw02.BasicCard;
import klondike.model.hw02.KlondikeModel;

/**
 * A mock that has a log of the inputs passed into it, for the purpose of checking the input is
 * what the model should be seeing properly. Specifically used for index matching between
 * 0-indexed items and 1-indexed items.
 */
public class InputLoggerMock implements KlondikeModel<BasicCard> {
  StringBuilder log;

  /**
   * Makes a mock model that logs the inputs for checking they are what the model should be seeing.
   *
   * @param ap the Appendable object representing the log
   */
  public InputLoggerMock(StringBuilder ap) {
    this.log = ap;
  }


  @Override
  public List createNewDeck() {
    return null;
  }

  @Override
  public void startGame(List deck, boolean shuffle, int numPiles, int numDraw)
      throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public void movePile(int srcPile, int numCards, int destPile)
      throws IllegalArgumentException, IllegalStateException {
    this.log.append("srcPile = " + Integer.toString(srcPile)
        + " numCards = " + Integer.toString(numCards)
        + " destPile  = " + Integer.toString(destPile) + "\n");
  }

  @Override
  public void moveDraw(int destPile) throws IllegalArgumentException, IllegalStateException {
    this.log.append("destPile  = " + Integer.toString(destPile) + "\n");
  }

  @Override
  public void moveToFoundation(int srcPile, int foundationPile)
      throws IllegalArgumentException, IllegalStateException {
    this.log.append("srcPile = " + Integer.toString(srcPile)
        + " foundationPile = " + Integer.toString(foundationPile) + "\n");
  }

  @Override
  public void moveDrawToFoundation(int foundationPile)
      throws IllegalArgumentException, IllegalStateException {
    this.log.append("foundationPile = " + Integer.toString(foundationPile) + "\n");
  }

  @Override
  public void discardDraw() throws IllegalStateException {

  }

  @Override
  public int getNumRows() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getNumPiles() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getNumDraw() throws IllegalStateException {
    return 0;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getPileHeight(int pileNum) throws IllegalArgumentException, IllegalStateException {
    this.log.append("pileNum = " + Integer.toString(pileNum) + "\n");
    return 0;
  }

  @Override
  public BasicCard getCardAt(int pileNum, int card)
      throws IllegalArgumentException, IllegalStateException {
    this.log.append("pileNum = " + Integer.toString(pileNum)
        + " card = " + Integer.toString(card) + "\n");
    return null;
  }

  @Override
  public BasicCard getCardAt(int foundationPile) throws IllegalArgumentException,
      IllegalStateException {
    this.log.append("foundationPile = " + Integer.toString(foundationPile) + "\n");
    return null;
  }

  @Override
  public boolean isCardVisible(int pileNum, int card)
      throws IllegalArgumentException, IllegalStateException {
    this.log.append("pileNum = " + Integer.toString(pileNum)
        + " card = " + Integer.toString(card) + "\n");
    return false;
  }

  @Override
  public List getDrawCards() throws IllegalStateException {
    return List.of();
  }

  @Override
  public int getNumFoundations() throws IllegalStateException {
    return 0;
  }
}
