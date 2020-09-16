package untitled.goose.framework.mock

import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.definitions.{BoardDefinition, Disposition, GameDefinitionBuilder, TileDefinition}
import untitled.goose.framework.model.entities.runtime.{Game, Piece, Player}
import untitled.goose.framework.model.rules.ruleset.PlayerOrderingType

import scala.collection.immutable.ListMap

object MatchMock {

  def board: BoardDefinition = BoardDefinition("mock", 10, Disposition.snake(10))

  def board2: BoardDefinition = BoardDefinition("mock2", Set(TileDefinition(11), TileDefinition("Prison")), Disposition.snake(10), TileDefinition(11))

  def p1: Player = Player("P1")

  def p2: Player = Player("P2")

  def players: ListMap[Player, Piece] = ListMap(p1 -> Piece(Colour.Default.Red), p2 -> Piece(Colour.Default.Blue))

  def default: Game = Game(GameDefinitionBuilder()
    .board(board)
    .actionRules(Set())
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
