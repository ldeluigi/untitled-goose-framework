package model.game

import engine.events.consumable.SkipTurnEvent
import engine.events.persistent.LoseTurnEvent
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

  "PimpedHistory.filterCycle" should "filter events by cycle" in {
    pending
  }

  "PimpedHistory.filterName" should "filter events by name" in {
    pending
  }

  "PimpedHistory.only" should "filter events by type and cast them" in {
    ph.only[LoseTurnEvent] should have size 0
    ph.only[SkipTurnEvent] should contain theSameElementsAs h
    ph.only[SkipTurnEvent].foreach(_.player.name should equal("a"))
  }

  "PimpedHistory.removeEvent" should "remove a specific event" in {
    pending
  }

  "PimpedHistory.remove[T]" should "remove N events of type T event" in {
    pending
  }

  "PimpedHistory.removeAll[T]" should "remove all the events of type T event" in {
    pending
  }

  "MutableStateExtensions.submitEvent" should "submit a given event in the right histories" in {
    pending
  }

  "MutableStateExtensions.saveEvent" should "save a consumable event onto the correct persistent history" in {
    pending
  }

  "GameStateExtensions.getTile(num)" should "return the tile with that number if it exists" in {
    pending
  }

  "GameStateExtensions.getTile(name)" should "return the tile with that name if it exists" in {
    pending
  }

  "GameStateExtensions.playerStopsTurn" should "return the turns on which a player has stopped on a tile" in {
    pending
  }

  "GameStateExtensions.playerLastTurn" should "return the last turn of a player" in {
    pending
  }


}
