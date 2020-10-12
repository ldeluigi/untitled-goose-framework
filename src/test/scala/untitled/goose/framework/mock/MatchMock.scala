package untitled.goose.framework.mock

import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.definitions._
import untitled.goose.framework.model.entities.runtime.PlayerDefinition.PlayerDefinitionImpl
import untitled.goose.framework.model.entities.runtime.{Game, Piece, PlayerDefinition}
import untitled.goose.framework.model.events.consumable.SkipTurnEvent
import untitled.goose.framework.model.rules.actionrules.AlwaysActionRule.AlwaysPermittedActionRule

import scala.collection.immutable.ListMap

object MatchMock {

  def board: BoardDefinition = BoardDefinition("mock", 10, Disposition.snake(10))

  def board2: BoardDefinition = BoardDefinition("mock2", Set(TileDefinition(11), TileDefinition("Prison")), Disposition.snake(10), TileDefinition(11))

  def p1: PlayerDefinition = PlayerDefinitionImpl("P1")

  def p2: PlayerDefinition = PlayerDefinitionImpl("P2")

  def players: ListMap[PlayerDefinition, Piece] = ListMap(p1 -> Piece(Colour.Default.Red), p2 -> Piece(Colour.Default.Blue))

  def action: Action = Action("test", s => SkipTurnEvent(s.currentPlayer, s.currentTurn, s.currentCycle))

  def default: Game = Game(GameDefinitionBuilder()
    .board(board)
    .actionRules(Set(AlwaysPermittedActionRule(6, action)))
    .behaviourRules(Seq())
    .cleanupRules(Seq())
    .playerOrderingType(PlayerOrderingType.Fixed)
    .playersRange(1 to 10)
    .build(), players)

  def alternative: Game = Game(GameDefinitionBuilder()
    .board(board2)
    .actionRules(Set())
    .behaviourRules(Seq())
    .cleanupRules(Seq())
    .playerOrderingType(PlayerOrderingType.Fixed)
    .playersRange(1 to 2)
    .build(), players)
}
