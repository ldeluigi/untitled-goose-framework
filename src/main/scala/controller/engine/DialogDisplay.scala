package controller.engine

import model.entities.DialogContent
import model.events.GameEvent

import scala.concurrent.Future

trait DialogDisplay {

  def display(dialogContent: DialogContent): Future[GameEvent]
}
