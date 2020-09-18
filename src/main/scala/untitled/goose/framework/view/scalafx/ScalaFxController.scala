package untitled.goose.framework.view.scalafx

import untitled.goose.framework.model.entities.runtime.Game
import untitled.goose.framework.view.scene.controller.ApplicationController
import untitled.goose.framework.view.scene.controller.ApplicationController.ApplicationControllerImpl

/** A controller for ScalaFx views. */
trait ScalaFxController extends ApplicationController

object ScalaFxController {

  /**
   * Factory method for a ScalaFxController.
   *
   * @param game The game that this view should manage and display.
   * @return a new ScalaFxController.
   */
  def apply(game: Game): ScalaFxController = new ApplicationControllerImpl(game) with ScalaFxController
}
