package klondike;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import klondike.model.hw02.BasicCard;
import klondike.model.hw02.BasicPile;
import klondike.model.hw02.Pile;
import klondike.model.hw02.Rank;
import klondike.model.hw02.Suit;
import org.junit.Before;
import org.junit.Test;

/**
 * Contains the test for BasicPile.
 */
public class PileTests {
  Pile smallExample;
  ArrayList<BasicCard> smallList;

  /**
   * Creates some Piles for testing.
   */
  @Before
  public void setUp() {
    smallList = new ArrayList<>();
    BasicCard aceHearts = new BasicCard(Rank.ACE, Suit.HEARTS);
    BasicCard twoDiamonds = new BasicCard(Rank.TWO, Suit.DIAMONDS);
    BasicCard threeHearts = new BasicCard(Rank.THREE, Suit.HEARTS);
    smallList.add(aceHearts);
    smallList.add(twoDiamonds);
    smallList.add(threeHearts);

    smallExample = new BasicPile(smallList);
  }

  @Test
  public void testEmptyPileConstructor() {
    assertEquals(new ArrayList<>(), new BasicPile().getList());
  }

  @Test
  public void testGetPileSize() {
    assertEquals(0, new BasicPile().getPileSize());
    assertEquals(3, smallExample.getPileSize());
  }

  @Test
  public void testContainsCorrectCards() {
    assertEquals(smallList, smallExample.getList());
  }

  @Test
  public void testCountVisibleCards() {
    assertEquals(3, smallExample.countVisibleCards());
  }

  @Test
  public void testIsCardVisible() {
    assertEquals(true, smallExample.isCardVisible(0));
  }
}
