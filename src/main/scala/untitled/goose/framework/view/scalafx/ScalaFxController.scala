package untitled.goose.framework.view.scalafx

import untitled.goose.framework.model.entities.runtime.Game
import untitled.goose.framework.view.scene.controller.ApplicationController
import untitled.goose.framework.view.scene.controller.ApplicationController.ApplicationControllerImpl

// TODO scaladoc
trait ScalaFxController extends ApplicationController

object ScalaFxController {
  def apply(game: Game): ScalaFxController = new ApplicationControllerImpl(game) with ScalaFxController
}
