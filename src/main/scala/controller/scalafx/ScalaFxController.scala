package controller.scalafx

import controller.{GameManager, ViewController}
import view.scalafx.GameScene

/** A controller that can manage a ScalaFx scene. More specifically, a [[GameScene]]. */
trait ScalaFxController extends ViewController with GameManager {

  /** Sets the ScalaFx scene.  */
  def setScene(gameScene: GameScene): Unit
}
