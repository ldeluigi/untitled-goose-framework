package model.rules.ruleset

import model.game.Player
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

  def randomOrder(seed: Long): PlayerOrdering = new PlayerOrdering {

    private var playerList: Seq[Player] = List()

    override def first(players: Set[Player]): Player = {
      Random.setSeed(seed)
      playerList = Random.shuffle(players).toList
      playerList.head
    }

    override def next(current: Player, players: Set[Player]): Player = {
      playerList = updateLocalSeq(playerList, players)
      playerList.lift((playerList.indexOf(current) + 1) % playerList.size).get
    }
  }

  /** A factory to create a custom players ordering.
   *
   * @param players the whole players set
   * @return a new custom player ordering
   */
  def givenOrder(players: Seq[Player]): PlayerOrdering = new PlayerOrdering {
    private var playerList: Seq[Player] = players


    override def first(players: Set[Player]): Player = {
      playerList = updateLocalSeq(playerList, players)
      playerList.head
    }

    override def next(current: Player, players: Set[Player]): Player = {
      playerList = updateLocalSeq(playerList, players)
      playerList.lift((playerList.indexOf(current) + 1) % playerList.size).get
    }
  }

  private def updateLocalSeq(localSeq: Seq[Player], players: Set[Player]): Seq[Player] = {
    var playerList = localSeq
    val removed = playerList diff players.toList
    val added = players.toList diff playerList
    playerList = playerList.filter(!removed.contains(_))
    playerList = playerList ++ added
    playerList
  }

}
