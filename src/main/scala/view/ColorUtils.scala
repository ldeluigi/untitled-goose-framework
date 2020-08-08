package view

import model.Color.{Color => ModelColor}
import scalafx.scene.paint.Color

object ColorUtils {

  def getColor(color: ModelColor): Color = {
    color match {
      case model.Color.Red => Color.Red
      case model.Color.Blue => Color.Blue
      case model.Color.Yellow => Color.Yellow
      case model.Color.Orange => Color.Orange
      case model.Color.Green => Color.Green
      case model.Color.Purple => Color.Purple
    }
  }
}
