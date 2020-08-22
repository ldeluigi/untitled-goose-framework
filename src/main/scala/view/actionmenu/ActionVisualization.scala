package view.actionmenu

import model.actions.Action
import scalafx.scene.control.Button
import view.ApplicationController

/** A custom button to handle custom behaviour for custom actions.
 */
trait ActionVisualization extends Button {

  onMouseClicked = _ => onClick()

  def onClick(): Unit
}

object ActionVisualization {

  private class ActionVisualizationImpl(action: Action, controller: ApplicationController) extends ActionVisualization {

    this.text = action.name

    /** Utility method to link an external behaviour to the actual button click handler.
     */
    override def onClick(): Unit = controller.resolveAction(action)
  }

  /** A factory which creates a new ActionVisualization button. */
  def apply(action: Action, controller: ApplicationController): ActionVisualization = new ActionVisualizationImpl(action, controller)
}
