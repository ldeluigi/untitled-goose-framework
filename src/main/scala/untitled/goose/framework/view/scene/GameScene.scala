package untitled.goose.framework.view.scene

import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent

import scala.concurrent.Promise

// TODO scaladoc
trait GameScene {

  /** When possible, delegates the task of updating the scene with the new information regarding the board,
   * re-renders now available actions and updates the logger.
   *
   * @param state            the new game's state.
   * @param availableActions the new available action for the player in turn.
   */
  def updateScene(state: GameState, availableActions: Set[Action]): Unit

  /**
   * Closes the stage.
   */
  def close(): Unit

  /** When possible, updates the logger with the newly happened event.
   *
   * @param event the event to append to the logger.
   */
  def logEvent(event: GameEvent): Unit

  /** When possible, creates a new dialog.
   *
   * @param dialogContent the content to add to the dialog.
   * @param promise       the promise used to check the action the user has chosen, passed to the new dialog itself.
   */
  def showDialog(dialogContent: DialogContent, promise: Promise[GameEvent]): Unit
}
