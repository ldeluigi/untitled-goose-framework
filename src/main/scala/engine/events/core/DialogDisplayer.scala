package engine.events.core

import engine.events.root.GameEvent
import model.entities.DialogContent

import scala.concurrent.Future

trait DialogDisplayer {

  def display(dialogContent: DialogContent): Future[GameEvent]
}
