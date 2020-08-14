package model.`match`

import engine.events.root.GameEvent
import model.Player
import model.entities.board.Piece

trait MatchState {

  def newTurnStarted: Boolean

  def currentTurn: Int

  def currentPlayer: Player

  def nextPlayer: Player

  def playerPieces: Map[Player, Piece]

  def matchBoard: MatchBoard

  def history: List[GameEvent]

}
