package untitled.goose.framework.view.scalafx

import scalafx.scene.paint.Color
import untitled.goose.framework.model
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.Colour.Colour

/** Matches the untitled.goose.framework.model's color to their ScalaFX actual counterparts. */
object ColorUtils {
  val ModelColor: Colour.type = model.Colour

  /**
   * @param color the application untitled.goose.framework.model's mapped color.
   * @return the matching ScalaFX color.
   */
  def getColor(color: Colour): Color = {
    color match {
      case ModelColor.Red => Color.Red
      case ModelColor.Blue => Color.Blue
      case ModelColor.Yellow => Color.Yellow
      case ModelColor.Orange => Color.Orange
      case ModelColor.Green => Color.Green
      case ModelColor.Purple => Color.Purple
    }
  }
}
