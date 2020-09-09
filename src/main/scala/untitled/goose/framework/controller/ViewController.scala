package untitled.goose.framework.controller

import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent

import scala.concurrent.Future

/** Used by the engine to control the view. */
trait ViewController {

  /**
   * Updates the view with the current game state.
   *
   * @param state The GameState. __Note__ that for lazy display, this object should be
   *              safe-copied because it's mutable and can be updated by the engine.
   */
  def update(state: GameState)

  /**
   * Displays a dialog to the user, waits asynchronously for the answer, and submits
   * the result as a GameEvent.
   *
   * @param content The data that fill the dialog text.
   * @return A future of GameEvent, the result of the player's choice.
   */
  def showDialog(content: DialogContent): Future[GameEvent]

  /** Closes the view. */
  def close(): Unit
}
