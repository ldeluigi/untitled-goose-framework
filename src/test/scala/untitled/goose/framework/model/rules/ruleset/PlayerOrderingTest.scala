package untitled.goose.framework.model.rules.ruleset

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.model.entities.runtime.Player
import untitled.goose.framework.model.entities.runtime.Player.PlayerImpl
import untitled.goose.framework.model.entities.runtime.PlayerDefinition.PlayerDefinitionImpl

import scala.util.Random

class PlayerOrderingTest extends AnyFlatSpec with Matchers {

  val p1: Player = PlayerImpl(PlayerDefinitionImpl("P1"))
  val p2: Player = PlayerImpl(PlayerDefinitionImpl("P2"))
  val players = Seq(p1, p2)

  behavior of "PlayerOrderingTest"

  it should "shuffle the given players into a randomized order" in {
    val p3: Player = PlayerImpl(PlayerDefinitionImpl("P3"))
    val p4: Player = PlayerImpl(PlayerDefinitionImpl("P4"))
    Random.setSeed(4)
    val ordering: PlayerOrdering = PlayerOrdering.randomOrder
    val playerList: Seq[Player] = List(p1, p2, p3, p4)

    ordering.first(playerList) should be(p1)
    ordering.next(p2, playerList) should be(p3)
  }

  it should "return a new custom player ordering" in {
    val ordering: PlayerOrdering = PlayerOrdering.fixed
    ordering.first(players) should be(p1)
    ordering.next(p1, players) should be(p2)
  }
}
