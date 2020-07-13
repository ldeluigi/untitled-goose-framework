package view

import scalafx.scene.Scene
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle

class BoardStage(tileNumber: Int) extends Scene {


}

object BoardStage {
  def apply(tileNumber: Int) = new BoardStage(tileNumber)
}
