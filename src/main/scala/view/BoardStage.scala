package view

import scalafx.scene.Scene
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle

class BoardStage(tileNumber: Int) extends Scene {
  this.fill = Black
  this.content = new HBox()
  var i = 0
  for (i <- 0 to tileNumber) {
    this.content.add(new Rectangle {
      x = 25 + (i * 100)
      y = 40
      width = 100
      height = 100
      fill = Red
    })
  }


}

object BoardStage {
  def apply(tileNumber: Int) = new BoardStage(tileNumber)
}
