package untitled.goose.framework.view.scalafx

import scalafx.scene.paint.Color
import untitled.goose.framework.model.Colour

/** Matches the untitled.goose.framework.model's colour to their ScalaFX actual counterparts. */
object ColorUtils {

  /**
   * @param color the application untitled.goose.framework.model's mapped colour.
   * @return the matching ScalaFX colour.
   */
  def getColor(color: Colour): Color = Color.color(color.red, color.green, color.blue, color.opacity)
}
