package engine.core

import engine.events.GameEvent
import model.entities.DialogContent

import scala.concurrent.Future

trait DialogDisplay {

  def display(dialogContent: DialogContent): Future[GameEvent]
}
