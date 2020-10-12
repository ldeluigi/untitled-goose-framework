package model.entities.runtime

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.GameStateExtensions._
import untitled.goose.framework.model.entities.runtime.PlayerDefinition.PlayerDefinitionImpl
import untitled.goose.framework.model.entities.runtime.{GameStateExtensions => _, _}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, SkipTurnEvent}
import untitled.goose.framework.model.events.persistent.LoseTurnEvent

class GameStateExtensionsTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  var skipTurnSequence: Seq[SkipTurnEvent] = Seq(SkipTurnEvent(PlayerDefinitionImpl("a"), 1, 1))
  var pimpedHistory: PimpedHistory[GameEvent] = new PimpedHistory[GameEvent](skipTurnSequence)

  "PimpedHistory.filterTurn" should "filter events by turn" in {
    skipTurnSequence = Seq(SkipTurnEvent(PlayerDefinitionImpl("a"), 1, 1))
    pimpedHistory = new PimpedHistory[GameEvent](skipTurnSequence)
    pimpedHistory.filterTurn(2) should have size 0
    pimpedHistory.filterTurn(1) should contain theSameElementsAs skipTurnSequence
  }

  "PimpedHistory.filterCycle" should "filter events by cycle" in {
    skipTurnSequence = Seq(SkipTurnEvent(PlayerDefinitionImpl("a"), 1, 1))
    pimpedHistory = new PimpedHistory[GameEvent](skipTurnSequence)
    pimpedHistory.filterCycle(2) should have size 0
    pimpedHistory.filterCycle(1) should contain theSameElementsAs skipTurnSequence
  }

  "PimpedHistory.filterName" should "filter events by name" in {
    skipTurnSequence = Seq(SkipTurnEvent(PlayerDefinitionImpl("a"), 1, 1))
    pimpedHistory = new PimpedHistory[GameEvent](skipTurnSequence)
    pimpedHistory.filterName("b") should have size 0
  }

  "PimpedHistory.only" should "filter events by type and cast them" in {
    skipTurnSequence = Seq(SkipTurnEvent(PlayerDefinitionImpl("a"), 1, 1))
    pimpedHistory = new PimpedHistory[GameEvent](skipTurnSequence)
    pimpedHistory.only[LoseTurnEvent] should have size 0
    pimpedHistory.only[SkipTurnEvent] should contain theSameElementsAs skipTurnSequence
    pimpedHistory.only[SkipTurnEvent].foreach(_.player.name should equal("a"))
  }

  "PimpedHistory.excludeEvent" should "remove a specific event" in {
    skipTurnSequence = Seq(SkipTurnEvent(PlayerDefinitionImpl("a"), 1, 1))
    pimpedHistory = new PimpedHistory[GameEvent](skipTurnSequence)
    pimpedHistory.excludeEvent(skipTurnSequence.head) should have size (0)
  }

  "PimpedHistory.skipOfType[T]" should "remove N events of type T event" in {
    skipTurnSequence = Seq(SkipTurnEvent(PlayerDefinitionImpl("a"), 1, 1))
    pimpedHistory = new PimpedHistory[GameEvent](skipTurnSequence)
    pimpedHistory.skipOfType[SkipTurnEvent](1) should have size (0)
  }

  "PimpedHistory.excludeEventType[T]" should "remove all the events of type T event" in {
    skipTurnSequence = Seq(SkipTurnEvent(PlayerDefinitionImpl("a"), 1, 1))
    pimpedHistory = new PimpedHistory[GameEvent](skipTurnSequence)
    pimpedHistory.excludeEventType[SkipTurnEvent]() should have size (0)
  }

  "MutableStateExtensions.submitEvent" should "submit a given event in the right histories" in {
    val gameMatch: Game = MatchMock.default
    val gameMutableState: GameState = gameMatch.currentState
    val skipTurnEvent: ConsumableGameEvent = SkipTurnEvent(gameMatch.currentState.currentPlayer.definition, gameMatch.currentState.currentTurn, gameMatch.currentState.currentCycle)

    gameMutableState.submitEvent(skipTurnEvent).consumableBuffer should contain(skipTurnEvent)
  }

  "MutableStateExtensions.saveEvent" should "save a consumable event onto the correct persistent history" in {
    val gameMatch: Game = MatchMock.default
    val gameMutableState: GameState = gameMatch.currentState
    val skipTurnEvent: ConsumableGameEvent = SkipTurnEvent(gameMatch.currentState.currentPlayer.definition, gameMatch.currentState.currentTurn, gameMatch.currentState.currentCycle)
    gameMutableState.saveEvent(skipTurnEvent).gameHistory should contain(skipTurnEvent)
  }

  "GameStateExtensions.getTile(num)" should "return the tile with that number if it exists" in {
    var gameMatch: Game = MatchMock.alternative
    val gameState: GameState = gameMatch.currentState
    val tile: Tile = Tile(TileDefinition(11))
    gameState.getTile(11).get should be(tile)
  }

  "GameStateExtensions.getTile(name)" should "return the tile with that name if it exists" in {
    var gameMatch: Game = MatchMock.alternative
    val gameState: GameState = gameMatch.currentState
    val tile: Tile = Tile(TileDefinition("Prison"))
    gameState.getTile("Prison").get should be(tile)
  }

  "GameStateExtensions.playerStopsTurn" should "return the turns on which a player has stopped on a tile" in {
    var gameMatch: Game = MatchMock.default
    val gameState: GameState = gameMatch.currentState
    pending
  }

  "GameStateExtensions.playerLastTurn" should "return the last turn of a player" in {
    var gameMatch: Game = MatchMock.default
    val gameState: GameState = gameMatch.currentState
    pending
  }

}
