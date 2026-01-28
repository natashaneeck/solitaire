package klondike.model.hw04;

import klondike.model.hw02.BasicKlondike;
import klondike.model.hw02.KlondikeModel;

/**
 * A factory that contains a static method that delivers a requested model of Klondike.
 */
public class KlondikeCreator {

  /**
   * Representing the different versions of Klondike.
   */
  public enum GameType { BASIC, WHITEHEAD }

  /**
   * A factory method that returns the requested type of KlondikeModel.
   *
   * @param type the game type being requested
   * @return a model implementation of KlondikeModel
   */
  public static KlondikeModel create(GameType type) {
    if (type == GameType.WHITEHEAD) {
      return new WhiteheadKlondike();
    } else {
      return new BasicKlondike();
    }
  }
}
