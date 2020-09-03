package model.entities.runtime

trait GameTemplate {
  def playersRange: Range

  def createGame(players: Seq[Player], playerPieces: Map[Player, Piece]): Game
}
