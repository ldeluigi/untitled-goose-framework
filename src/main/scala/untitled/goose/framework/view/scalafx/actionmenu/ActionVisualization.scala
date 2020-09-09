package untitled.goose.framework.view.scalafx.actionmenu

import scalafx.scene.control.Button
import untitled.goose.framework.controller.GameManager
import untitled.goose.framework.model.actions.Action

/** A custom button to handle custom behaviour for custom actions.
 */
trait ActionVisualization extends Button {

  onMouseClicked = _ => onClick()

  def onClick(): Unit
}

object ActionVisualization {

  private class ActionVisualizationImpl(action: Action, controller: GameManager) extends ActionVisualization {

    this.text = action.name

    /** Utility method to link an external behaviour to the actual button click handler.
     */
    override def onClick(): Unit = controller.resolveAction(action)
  }

  /** A factory which creates a new ActionVisualization button. */
  def apply(action: Action, controller: GameManager): ActionVisualization = new ActionVisualizationImpl(action, controller)
}