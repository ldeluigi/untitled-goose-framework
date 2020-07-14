package view

import model.actions.Action
import scalafx.scene.control.Button
import scalafx.scene.layout.Pane

trait ActionMenu {

  def displayActions(actions: List[Action])

}

object ActionMenu {
  def apply() = ActionMenuImpl()
}

case class ActionMenuImpl() extends Pane with ActionMenu {

  this.children.add(new Button("CIAO"))

  override def displayActions(actions: List[Action]): Unit = ???
}

