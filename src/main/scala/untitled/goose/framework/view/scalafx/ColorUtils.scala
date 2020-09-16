package untitled.goose.framework.view.scalafx

import scalafx.scene.paint.Color
import untitled.goose.framework.model.Colour

/** Matches the untitled.goose.framework.model's color to their ScalaFX actual counterparts. */
object ColorUtils {

  /**
   * @param color the application untitled.goose.framework.model's mapped color.
   * @return the matching ScalaFX color.
   */
  def getColor(color: Colour): Color = Color.color(color.red, color.green, color.blue, color.opacity)
}
