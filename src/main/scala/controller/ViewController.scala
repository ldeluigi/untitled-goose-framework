package controller

import model.entities.DialogContent
import model.events.GameEvent
import model.game.GameState

import scala.concurrent.Future

trait ViewController {

  def update(state: GameState)

  def showDialog(content: DialogContent): Future[GameEvent]

  def logAsyncEvent(event: GameEvent)

  def close(): Unit
}
