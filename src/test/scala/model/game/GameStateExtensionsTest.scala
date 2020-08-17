package model.game

import engine.events.root.GameEvent
import engine.events.{LoseTurnEvent, SkipTurnEvent}
import model.Player
import model.game.GameStateExtensions.PimpedHistory
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GameStateExtensionsTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  var h: Seq[SkipTurnEvent] = Seq(SkipTurnEvent(Player("a"), 1))
  var ph: PimpedHistory[GameEvent] = new PimpedHistory[GameEvent](h)

  override protected def beforeEach(): Unit = {
    h = Seq(SkipTurnEvent(Player("a"), 1))
    ph = new PimpedHistory[GameEvent](h)
  }

  "PimpedHistory.filterTurn" should "filter events by turn" in {
    ph.filterTurn(2) should have size 0
    ph.filterTurn(1) should contain theSameElementsAs h
  }

  "PimpedHistory.consumeAll" should "consume all events" in {
    h.filter(_.isConsumed) should have size 0
    ph.consumeAll()
    h.filter(_.isConsumed) should have size 1
  }

  "PimpedHistory.filterNotConsumed" should "filter not consumed events" in {
    ph.filterNotConsumed() should contain theSameElementsAs h
    h.foreach(_.consume())
    ph.filterNotConsumed() should have size 0
  }

  "PimpedHistory.only" should "filter events by type and cast them" in {
    ph.only[LoseTurnEvent] should have size 0
    ph.only[SkipTurnEvent] should contain theSameElementsAs h
    ph.only[SkipTurnEvent].foreach(_.source.name should equal("a"))
  }

  it should "MatchStateExtensions" in {
    pending // TODO after tile identifier
  }

}
