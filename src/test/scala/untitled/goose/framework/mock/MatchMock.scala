package untitled.goose.framework.mock

import untitled.goose.framework.model.entities.runtime.{Game, Piece, Player, Position}
import untitled.goose.framework.model.rules.ruleset.{PlayerOrdering, PlayerOrderingType, PriorityRuleSet}
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.definitions.{BoardDefinition, Disposition, GameDefinitionBuilder}

import scala.collection.immutable.ListMap

object MatchMock {
  def board: BoardDefinition = BoardDefinition("mock", 10, Disposition.snake(10))
  def p1: Player = Player("P1")
  def p2: Player = Player("P2")
  def players: ListMap[Player, Piece] = ListMap(p1 -> Piece(Colour.Red), p2 -> Piece(Colour.Blue))

  def default: Game = Game(GameDefinitionBuilder()
    .board(board)
    .actionRules(Set())
    .behaviourRules(Seq())
    .cleanupRules(Seq())
    .playerOrderingType(PlayerOrderingType.Fixed)
    .playersRange(1 to 10)
    .build, players)
}
