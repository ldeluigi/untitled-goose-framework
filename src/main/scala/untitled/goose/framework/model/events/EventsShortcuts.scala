package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.definitions.TileIdentifier.Group
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.entities.runtime.GameStateExtensions.GameStateExtensions
import untitled.goose.framework.model.events.consumable._
import untitled.goose.framework.model.events.persistent.{GainTurnEvent, LoseTurnEvent}
import untitled.goose.framework.model.events.special.{ExitEvent, NoOpEvent}

trait EventsShortcuts {

  /*special*/
  def ExitGame: GameState => ExitEvent.type = _ => ExitEvent

  def Nothing: GameState => NoOpEvent.type = _ => NoOpEvent

  /*consumable*/
  def InvertMovement: GameState => InvertMovementEvent = s => InvertMovementEvent(s.currentPlayer, s.currentTurn, s.currentCycle)

  def SkipTurn: GameState => SkipTurnEvent = s => SkipTurnEvent(s.currentPlayer, s.currentTurn, s.currentCycle)

  def MakeSteps(steps: Int): GameState => StepMovementEvent = s => StepMovementEvent(steps, s.currentPlayer, s.currentTurn, s.currentCycle)

  def TeleportTo(tileName: String): GameState => TeleportEvent = s => TeleportEvent(s.getTile(tileName).get.definition, s.currentPlayer, s.currentTurn, s.currentCycle)

  def TeleportTo(tileNumber: Int): GameState => TeleportEvent = s => TeleportEvent(s.getTile(tileNumber).get.definition, s.currentPlayer, s.currentTurn, s.currentCycle)

  def TeleportToFirst(tileGroup: Group): GameState => TeleportEvent = s => TeleportEvent(s.getFirstTileOf(tileGroup).get.definition, s.currentPlayer, s.currentTurn, s.currentCycle)

  def TurnShouldEnd: GameState => TurnShouldEndEvent = s => TurnShouldEndEvent(s.currentTurn, s.currentCycle)

  def Victory: GameState => VictoryEvent = s => VictoryEvent(s.currentPlayer, s.currentTurn, s.currentCycle)

  /*persistent*/
  def GainTurn: GameState => GainTurnEvent = s => GainTurnEvent(s.currentPlayer, s.currentTurn, s.currentCycle)

  def LoseTurn: GameState => LoseTurnEvent = s => LoseTurnEvent(s.currentPlayer, s.currentTurn, s.currentCycle)

}
