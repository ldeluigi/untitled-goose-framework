package untitled.goose.framework.model.entities.runtime.functional

import untitled.goose.framework.model.entities.runtime.{Game, GameState}

trait GameUpdate {

  def updateState(update: GameState => GameState): Game

  def updateState(state: GameState): Game

  def toNextPlayer: GameState
}

object GameUpdate {

  implicit class GameUpdateImpl(game: Game) extends GameUpdate {
    override def updateState(update: GameState => GameState): Game = ???

    override def updateState(state: GameState): Game = ???

    override def toNextPlayer: GameState = ???
  }

}
