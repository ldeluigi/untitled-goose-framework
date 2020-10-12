package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.GameDefinition

import scala.collection.immutable.ListMap

/** A game encapsulates runtime dynamic information
 * together with static definitions of a goose-game.
 */
trait Game extends Defined[GameDefinition] {

  /** The game current state. */
  def currentState: GameState
}

object Game {

  case class GameImpl(definition: GameDefinition, currentState: GameState) extends Game

  /** A factory that creates a game with a given board definition, players, pieces and rules. */
  def apply(definition: GameDefinition, players: ListMap[PlayerDefinition, Piece]): Game =
    GameImpl(definition,
      GameState(players.keys.toSeq,
        definition.playerOrdering.first,
        players,
        Board(definition.board))
    )
}
