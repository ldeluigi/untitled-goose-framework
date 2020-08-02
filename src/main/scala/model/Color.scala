package model

import scala.util.Random

object Color extends Enumeration {
  def random: Color = {
    val n = Random.nextInt(Color.values.size)
    Color.values.iterator.drop(n).next
  }

  type Color = Value
  val Red, Blue, Yellow, White, Black, Orange, Green, Purple = Value
}
