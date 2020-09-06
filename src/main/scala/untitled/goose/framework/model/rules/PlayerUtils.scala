package untitled.goose.framework.model.rules

import untitled.goose.framework.model.entities.runtime.Player

import scala.util.Random

object PlayerUtils {

  private def random[T](s: Set[T]): T = {
    val n = Random.nextInt(s.size)
    s.iterator.drop(n).next
  }

  def selectRandom(players: Set[Player]): Player = random(players)
}

