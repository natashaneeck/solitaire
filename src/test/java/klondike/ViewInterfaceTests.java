package klondike;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import klondike.model.hw02.BasicCard;
import klondike.model.hw02.BasicKlondike;
import klondike.model.hw02.KlondikeModel;
import klondike.view.KlondikeTextualView;
import klondike.view.TextualView;
import org.junit.Test;


/**
 * Tests the public view interface methods, like the constructor and the toString().
 */
public class ViewInterfaceTests {

  @Test
  public void testBasicConstructionString() {
    KlondikeModel<BasicCard> model = new BasicKlondike();
    model.startGame(model.createNewDeck(), false, 7, 3);
    TextualView example = new KlondikeTextualView(model);

    String expectedStr = "Draw: 3♡, 4♡, 5♡\n"
        + "Foundation: <none>, <none>, <none>, <none>\n"
        + " A♢  ?  ?  ?  ?  ?  ?\n"
        + "    8♢  ?  ?  ?  ?  ?\n"
        + "       A♣  ?  ?  ?  ?\n"
        + "          6♣  ?  ?  ?\n"
        + "            10♣  ?  ?\n"
        + "                K♣  ?\n"
        + "                   2♡";

    assertEquals(expectedStr, example.toString());
  }


  @Test
  public void testEmptyPileNoDrawConstructionString() {
    KlondikeModel<BasicCard> model = new BasicKlondike();
    List<BasicCard> baseDeck = model.createNewDeck();
    String[] desiredCards = {"A♡", "2♡", "3♡", "A♣", "2♣", "3♣"};
    List<BasicCard> testingDeck = new ArrayList<>();

    for (BasicCard card : baseDeck) {
      if (Arrays.asList(desiredCards).contains(card.toString())) {
        testingDeck.add(card);
      }
    }

    model.startGame(testingDeck, false, 3, 1);
    model.moveToFoundation(0, 0);
    TextualView example = new KlondikeTextualView(model);

    String expectedStr = "Draw: \n"
        + "Foundation: A♣, <none>\n"
        + "  X  ?  ?\n"
        + "    A♡  ?\n"
        + "       3♡";

    assertEquals(expectedStr, example.toString());
  }
}


