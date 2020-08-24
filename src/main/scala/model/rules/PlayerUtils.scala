package model.rules

import model.Player

import scala.util.Random

object PlayerUtils {

  /** Randomization utility method.
   *
   * @return a randomized object
   */
  private def random[T](s: Set[T]): T = {
    val n = Random.nextInt(s.size)
    s.iterator.drop(n).next
  }

  /** Selects a random player.
   *
   * @param players the whole players set from which to select the random player
   * @return the randomly selected player
   */
  def selectRandom(players: Set[Player]): Player = random(players)
}

