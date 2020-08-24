package model.rules.ruleset

import model.Player
import model.rules.PlayerUtils

import scala.util.Random

/** Defines the concept of player orderings. */
trait PlayerOrdering {

  /** Returns the first player of the game
   *
   * @param players the player set from which take the first player
   * @return the first player of the game
   */
  def first(players: Set[Player]): Player

  /** Returns the next player of the game
   *
   * @param current the player from which select the next from
   * @param players the whole players set
   * @return the next player
   */
  def next(current: Player, players: Set[Player]): Player

}

object PlayerOrdering {

  /** A factory to create a full random players ordering.
   *
   * @return a randomized player set
   */
  def fullRandom: PlayerOrdering = new PlayerOrdering {

    /** Generates a random first player.
     *
     * @param players the player set from which generate the first random player
     * @return the first random player of the game
     */
    override def first(players: Set[Player]): Player = PlayerUtils.selectRandom(players)

    /** Generates a next random player.
     *
     * @param current the player from which select the next random player from
     * @param players the whole players set
     * @return the next random player
     */
    override def next(current: Player, players: Set[Player]): Player = PlayerUtils.selectRandom(players)
  }

  /** A factory to create a non-random players ordering.
   *
   * @return a non-random player ordering.
   */
  def orderedRandom: PlayerOrdering = new PlayerOrdering {

    private var playerList: Seq[Player] = List()

    /** Generates the first player of the game.
     *
     * @param players the player set from which take the first player
     * @return the first player of the game
     */
    override def first(players: Set[Player]): Player = {
      playerList = Random.shuffle(players).toList
      playerList.head
    }

    /** Generates the next player.
     *
     * @param current the player from which select the next from
     * @param players the whole players set
     * @return the next player
     */
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

  /** Updates a sequence of platers.
   *
   * @param localSeq the sequence to be updated
   * @param players  the whole players set
   * @return the updated player sequence
   */
  private def updateLocalSeq(localSeq: Seq[Player], players: Set[Player]): Seq[Player] = {
    var playerList = localSeq
    val removed = playerList diff players.toList
    val added = players.toList diff playerList
    playerList = playerList.filter(!removed.contains(_))
    playerList = playerList ++ added
    playerList
  }

}
