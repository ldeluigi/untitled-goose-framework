package model.rules

import model.actions.StepForwardAction
import model.entities.board.{Position, TileDefinition}
import model.rules.ruleset.{PriorityRuleSet, RuleSet}
import model.{MatchState, Player, Tile}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PriorityRuleSetTest extends AnyFlatSpec with Matchers {

  val thisClass = "PriorityRuleSet"

  val startTile: Tile = Tile(TileDefinition(0))

  val startPlayer: Player = Player("test")

  val myRule: ActionRule = (_: MatchState) =>
    Set(ActionAvailability(StepForwardAction()))

  val dumbRuleSet: RuleSet = PriorityRuleSet(
    t => Position(t.toList.min),
    _.head,
    ActionRule(),
    myRule
  )

  thisClass should "select a first player" in {
    val player = dumbRuleSet first Set(startPlayer)
    player.name should be(startPlayer.name)
  }
}
