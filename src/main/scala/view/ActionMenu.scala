package view

import model.actions.Action
import scalafx.scene.control.Button
import scalafx.scene.layout.Pane

trait ActionMenu {

  def displayActions(actions: List[Action])

}

object ActionMenu {
  def apply(width: Int, height: Int) = ActionMenuImpl(width, height)
}

case class ActionMenuImpl(widthSize: Int, heightSize: Int) extends Pane with ActionMenu {

  this.setMinSize(widthSize, heightSize)
  this.setMaxSize(widthSize, heightSize)

  this.children.add(new Button("CIAO"))

  override def displayActions(actions: List[Action]): Unit = ???
}

