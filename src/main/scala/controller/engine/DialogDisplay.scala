package controller.engine

import model.entities.DialogContent
import model.events.GameEvent

import scala.concurrent.Future

/** A DialogDisplay can display a DialogContent and return a GameEvent asynchronously. */
trait DialogDisplay {

  /**
   * Displays a dialog to the user, waits asynchronously for the answer, and submits
   * the result as a GameEvent.
   * @param dialogContent The data that fill the dialog text.
   * @return A future of GameEvent, the result of the player's choice.
   */
  def display(dialogContent: DialogContent): Future[GameEvent]
}
