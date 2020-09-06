package untitled.goose.framework.model

import scala.util.Random

object Color extends Enumeration {

  /** Selects a random domain-defined color.
   *
   * @return a randomized domain-defined color
   */
  def random: Color = {
    val n = Random.nextInt(Color.values.size)
    Color.values.iterator.drop(n).next
  }

  type Color = Value
  val Red, Blue, Yellow, Orange, Green, Purple = Value
}
