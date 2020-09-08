package untitled.goose.framework.model

import scala.util.Random

/** Colors a piece can have. */
object Colour extends Enumeration {

  /** Selects a random domain-defined color.
   *
   * @return a randomized domain-defined color.
   */
  def random: Colour = {
    val n = Random.nextInt(Colour.values.size)
    Colour.values.iterator.drop(n).next
  }

  type Colour = Value
  val Red, Blue, Yellow, Orange, Green, Purple = Value
}
