package untitled.goose.framework.model.rules.ruleset

import untitled.goose.framework.model.entities.runtime.Player
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class PlayerOrderingTest extends AnyFlatSpec with Matchers {

  val p1: Player = Player("P1")
  val p2: Player = Player("P2")
  val players = Seq(p1, p2)

  behavior of "PlayerOrderingTest"

  it should "shuffle the given players into a randomized order" in {
    val p3: Player = Player("P3")
    val p4: Player = Player("P4")
    val ordering: PlayerOrdering = PlayerOrdering.randomOrder(4)
    val playerList: Seq[Player] = List(p1, p2, p3, p4)
    ordering.first(playerList.toSet).equals(p1)
    ordering.next(p3, playerList.toSet).equals(p4)
  }

  it should "return a new custom player ordering" in {
    val ordering: PlayerOrdering = PlayerOrdering.givenOrder(players)
    ordering.first(players.toSet).equals(p1)
    ordering.next(p1, players.toSet).equals(p2)
  }
}
