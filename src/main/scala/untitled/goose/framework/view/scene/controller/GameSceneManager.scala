package untitled.goose.framework.view.scene.controller

import untitled.goose.framework.view.scene.GameScene

/**
 * A GameSceneManager is able to change scene. In particular,
 * it can set a GameScene as the current scene.
 */
trait GameSceneManager {

  /**
   * Sets the given GameScene as the current visible game scene.
   *
   * @param gameScene the GameScene to show.
   */
  def setGameScene(gameScene: GameScene): Unit
}
