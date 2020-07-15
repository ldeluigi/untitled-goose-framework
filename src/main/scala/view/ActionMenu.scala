package view

import model.{MatchState, Player}
import model.actions.Action
import scalafx.scene.control.Button
import scalafx.scene.layout.Pane

trait ActionMenu {

  def displayActions(actions: List[Action])

}

object ActionMenu {
  def apply(boardView: BoardView) = ActionMenuImpl(boardView)
}

case class ActionMenuImpl(val boardView: BoardView) extends Pane with ActionMenu {

  override def displayActions(actions: List[Action]): Unit = ???
}

