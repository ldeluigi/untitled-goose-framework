package model.rules.ruleset

import model.Player
import model.rules.PlayerUtils

import scala.util.Random

trait PlayerOrdering {

  def first(players: Set[Player]): Player

  def next(current: Player, players: Set[Player]): Player

}

object PlayerOrdering {

  def fullRandom: PlayerOrdering = new PlayerOrdering {

    override def first(players: Set[Player]): Player = PlayerUtils.selectRandom(players)

    override def next(current: Player, players: Set[Player]): Player = PlayerUtils.selectRandom(players)
  }

  def orderedRandom: PlayerOrdering = new PlayerOrdering {

    var playerList: List[Player] = List()

    override def first(players: Set[Player]): Player = {
      playerList = Random.shuffle(players).toList
      playerList.head
    }

    override def next(current: Player, players: Set[Player]): Player = {
      val removed = playerList diff players.toList
      val added = players.toList diff playerList
      playerList = playerList.filter(!removed.contains(_))
      playerList = playerList ++ added
      playerList.lift((playerList.indexOf(current) + 1) % playerList.size).get
    }
  }

}
