package klondike.view;

import java.io.IOException;
import klondike.model.hw02.Card;
import klondike.model.hw02.KlondikeModel;

/**
 * The representation of an ongoing Klondike game as a String game board.
 */
public class KlondikeTextualView implements TextualView {
  private final KlondikeModel<?> model;
  private Appendable out;

  /**
   * Constructor to make a TextualView for Klondike.
   *
   * @param model represents the game being played
   */
  public KlondikeTextualView(KlondikeModel<?> model) {
    this.model = model;
  }

  /**
   * Creates a TextualView for Klondike with an Appendable object for interaction.
   *
   * @param model represents the game being played
   * @param out the output the user will be shown
   */
  public KlondikeTextualView(KlondikeModel<?> model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  @Override
  public String toString() {
    return this.drawPileString() + "\n" + this.foundationPileString() + "\n"
        + this.cascadePileString();
  }

  /**
   * Creates the String representing the draw card list and its contents.
   *
   * @return the draw cards as a String
   */
  private String drawPileString() {
    String cardString = "Draw: ";
    if (model.getDrawCards().isEmpty()) {
      return cardString;
    }

    for (Card card : model.getDrawCards()) {
      cardString += card.toString() + ", ";
    }
    return cardString.substring(0, Math.max(cardString.length() - 2, 4));
  }

  /**
   * Creates the String representing the top cards in each foundation pile. Empty piles are shown
   * as none.
   *
   * @return the foundation card pile as a String
   */
  private String foundationPileString() {
    String cardString = "Foundation: ";
    for (int pileIndex = 0; pileIndex < model.getNumFoundations(); pileIndex++) {
      Card topCard = model.getCardAt(pileIndex);
      String toAdd = "";

      if (topCard == null) {
        toAdd = "<none>";
      } else {
        toAdd = topCard.toString();
      }
      cardString += toAdd + ", ";
    }
    return cardString.substring(0, Math.max(cardString.length() - 2, 10));
  }

  /**
   * Formats the String representing the cascade piles.
   *
   * @return the representation of the cascade piles as a String
   */
  private String cascadePileString() {
    String str = "";
    for (int row = 0; row < this.model.getNumRows(); row++) {
      for (int pileIndex = 0; pileIndex < this.model.getNumPiles(); pileIndex++) {
        if (this.model.getPileHeight(pileIndex) <= row) {
          str += this.formatEmpty(row == 0);
        } else {
          str += this.formatCard(pileIndex, row);
        }
      }
      str += "\n";
    }
    return str.substring(0, Math.max(str.length() - 1, 0));
  }

  /**
   * Formats the textual representation of the given card. If a card is not revealed, it is
   * displayed as a ?. All representations are standardized to three chars, right-aligned.
   *
   * @param pileIndex the cascade pile the card is in (0-indexed)
   * @param row the row the card is in inside of the pile (0-indexed)
   * @return a String of three characters, showing the card value or ?.
   */
  private String formatCard(int pileIndex, int row) {
    if (!this.model.isCardVisible(pileIndex, row)) {
      return "  ?";
    }

    String str = this.model.getCardAt(pileIndex, row).toString();
    if (str.length() < 3) {
      str = " " + str;
    }
    return str;
  }

  /**
   * Formats the textual representation of when there is no card at a given index. When the pile
   * is empty as a whole, it is shown as an X.
   *
   * @param bottom whether the card index is at the top of the pile
   * @return a String to use as a spacer in the textual representation when there is no card
   */
  private String formatEmpty(boolean bottom) {
    if (bottom) {
      return "  X";
    }
    return "   ";
  }

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render() throws IOException {
    out.append(this.toString()).append("\n");
  }
}
