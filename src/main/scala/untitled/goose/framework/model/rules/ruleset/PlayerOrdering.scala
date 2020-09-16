package untitled.goose.framework.model.rules.ruleset

import untitled.goose.framework.model.entities.runtime.Player

import scala.util.Random

/** Defines the order of players during a game. */
trait PlayerOrdering {

  /** Who goes first. */
  def first(players: Seq[Player]): Player

  /** Who is next, based on the current player. */
  def next(currentPlayer: Player, players: Seq[Player]): Player

}

object PlayerOrdering {

  /** Every turn, a random player is chosen. */
  def fullRandom: PlayerOrdering = new PlayerOrdering {
    private def random[T](s: Set[T]): T = {
      val n = Random.nextInt(s.size)
      s.iterator.drop(n).next
    }

    private def selectRandom(players: Set[Player]): Player = random(players)

    override def first(players: Seq[Player]): Player = selectRandom(players.toSet)

    override def next(currentPlayer: Player, players: Seq[Player]): Player = selectRandom(players.toSet)
  }

  /** List of players is shuffled, then is kept fixed for the entire game. */
  def randomOrder: PlayerOrdering = new PlayerOrdering {

    private var playerList: Seq[Player] = List()

    override def first(players: Seq[Player]): Player = {
      playerList = Random.shuffle(players).toList
      playerList.head
    }

    override def next(currentPlayer: Player, players: Seq[Player]): Player = {
      playerList = updateLocalSeq(playerList, players)
      playerList.lift((playerList.indexOf(currentPlayer) + 1) % playerList.size).get
    }
  }

  /** Keeps the order fixed. */
  def fixed: PlayerOrdering = new PlayerOrdering {
    private var playerList: Seq[Player] = List()


    override def first(players: Seq[Player]): Player = {
      playerList = players
      playerList.head
    }

    override def next(currentPlayer: Player, players: Seq[Player]): Player = {
      playerList = updateLocalSeq(playerList, players)
      playerList.lift((playerList.indexOf(currentPlayer) + 1) % playerList.size).get
    }
  }

  private def updateLocalSeq(localSeq: Seq[Player], players: Seq[Player]): Seq[Player] = {
    var playerList = localSeq
    val removed = playerList diff players.toList
    val added = players.toList diff playerList
    playerList = playerList.filter(!removed.contains(_))
    playerList = playerList ++ added
    playerList
  }

}
