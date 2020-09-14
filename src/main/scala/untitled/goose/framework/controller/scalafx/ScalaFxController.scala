package untitled.goose.framework.controller.scalafx

import untitled.goose.framework.controller.{GameManager, ViewController}
import untitled.goose.framework.view.scalafx.GameScene

/** A untitled.goose.framework.controller that can manage a ScalaFx scene. More specifically, a GameScene. */
trait ScalaFxController extends ViewController with GameManager {

  /** Sets the ScalaFx scene.  */
  def setScene(gameScene: GameScene): Unit
}
