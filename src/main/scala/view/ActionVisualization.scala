package view

import model.actions.Action
import scalafx.scene.control.Button

trait ActionVisualization extends Button {

  onMouseClicked = _ => onClick()

  def onClick(): Unit
}

object ActionVisualization {

  private class ActionVisualizationImpl(action: Action, controller: ApplicationController) extends ActionVisualization {

    this.text = action.name

    override def onClick(): Unit = controller.resolveAction(action)
  }

  def apply(action: Action, controller: ApplicationController): ActionVisualization = new ActionVisualizationImpl(action, controller)
}
