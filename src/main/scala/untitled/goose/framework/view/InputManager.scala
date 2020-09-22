package untitled.goose.framework.view

import untitled.goose.framework.model.actions.Action

/** A turn based user input manager, with few, simple controls. */
trait InputManager {

  /**
   * Resolves an action for the current player in a turn-based game.
   *
   * @param action The Action to resolve in this turn.
   */
  def resolveAction(action: Action)

  /** Stops the game. */
  def stopGame(): Unit
}
