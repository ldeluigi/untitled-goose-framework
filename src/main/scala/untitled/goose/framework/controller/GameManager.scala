package untitled.goose.framework.controller

import untitled.goose.framework.model.actions.Action

/** A turn based game manager, with few controls. */
trait GameManager {

  /**
   * Resolves an action for the current player in a turn-based game.
   * @param action The Action to resolve in this turn.
   */
  def resolveAction(action: Action)

  /** Stops the game. */
  def stopGame(): Unit
}
