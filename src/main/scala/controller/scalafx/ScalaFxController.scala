package controller.scalafx

import controller.{CommandSender, ViewController}
import view.scalafx.GameScene

/** A controller that can manage a ScalaFx scene. More specifically, a [[GameScene]]. */
trait ScalaFxController extends ViewController with CommandSender {

  /** Sets the ScalaFx scene.  */
  def setScene(gameScene: GameScene): Unit
}
