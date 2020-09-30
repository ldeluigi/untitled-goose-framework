package untitled.goose.framework.view.scalafx.actionmenu

import scalafx.scene.control.Button
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.view.InputManager

/** A custom button to handle custom behaviour for custom actions.
 */
trait ActionVisualization extends Button {

  onMouseClicked = _ => onClick()

  /** Utility method to link an external behaviour to the actual button click handler that solves the selected action.
   */
  def onClick(): Unit
}

object ActionVisualization {

  private class ActionVisualizationImpl(action: Action, controller: InputManager) extends ActionVisualization {

    this.text = action.name

    override def onClick(): Unit = controller.resolveAction(action)
  }

  /** A factory which creates a new ActionVisualization button. */
  def apply(action: Action, controller: InputManager): ActionVisualization = new ActionVisualizationImpl(action, controller)
}
