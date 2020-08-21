package model.game

import engine.events.consumable.SkipTurnEvent
import engine.events.persistent.player.LoseTurnEvent
import engine.events.{GameEvent, consumable}
import model.Player
import model.game.GameStateExtensions.PimpedHistory
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GameStateExtensionsTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  var h: Seq[SkipTurnEvent] = Seq(consumable.SkipTurnEvent(Player("a"), 1, 1))
  var ph: PimpedHistory[GameEvent] = new PimpedHistory[GameEvent](h)

  override protected def beforeEach(): Unit = {
    h = Seq(consumable.SkipTurnEvent(Player("a"), 1, 1))
    ph = new PimpedHistory[GameEvent](h)
  }

  "PimpedHistory.filterTurn" should "filter events by turn" in {
    ph.filterTurn(2) should have size 0
    ph.filterTurn(1) should contain theSameElementsAs h
  }

  "PimpedHistory.only" should "filter events by type and cast them" in {
    ph.only[LoseTurnEvent] should have size 0
    ph.only[SkipTurnEvent] should contain theSameElementsAs h
    ph.only[SkipTurnEvent].foreach(_.player.name should equal("a"))
  }
}
