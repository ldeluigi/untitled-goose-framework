package model.rules.ruleset

import model.actions.{Action, StepForwardAction}
import model.entities.board.{Position, TileDefinition}
import model.rules.actionrules.AlwaysActionRule.{AlwaysNegatedActionRule, AlwaysPermittedActionRule}
import model.{Player, Tile}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec

class PriorityRuleSetTest extends AnyFlatSpec with BeforeAndAfterEach {

  val t1: Tile = Tile(TileDefinition(1))
  val t2: Tile = Tile(TileDefinition(2))
  val tileSet: Set[Tile] = Set(t1, t2)
  val firstPosition: Set[Tile] => Position = tiles => Position(tiles.toList.sorted.take(1).head)
  val playersRange: Range = 1 to 10
  val p1: Player = Player("P1")
  val p2: Player = Player("P2")
  val ordering: PlayerOrdering = PlayerOrdering.givenOrder(Seq(p1, p2))

  var ruleSet: PriorityRuleSet = PriorityRuleSet(firstPosition, ordering, playersRange)

  override protected def beforeEach(): Unit = {
    ruleSet = PriorityRuleSet(firstPosition, ordering, playersRange)
  }

  val myAction: Action = StepForwardAction()


  "A priority based rule set" should "define the starting position from a given set of tiles" in {
    assert(ruleSet.startPosition(tileSet).equals(Position(t1)))
  }

  it should "define the first player to play from a given set" in {
    assert(ruleSet.first(Set(p1, p2)).equals(p1))
  }

  it should "define the next player given the current one" in {
    assert(ruleSet.nextPlayer(p1, Set(p1, p2)).equals(p2))
  }

  it should "permit an action if the priority of the negation is lower than the permission" in {
    val negation = AlwaysNegatedActionRule(1, myAction)
    val permission = AlwaysPermittedActionRule(2, myAction)
    val ruleSet = PriorityRuleSet(admissiblePlayers = 1 to 10, actionRules = Set(negation, permission))
    assert(ruleSet.actions(null).contains(myAction))
  }

  it should "negate the action if the priority of the permission is lower than the negation" in {
    val negation = AlwaysNegatedActionRule(2, myAction)
    val permission = AlwaysPermittedActionRule(1, myAction)
    val ruleSet = PriorityRuleSet(admissiblePlayers = 1 to 10, actionRules = Set(negation, permission))
    assert(!ruleSet.actions(null).contains(myAction))
  }


  // TODO complete test
  it should "return the operations to be executed in a state" in {
    pending
  }

  // TODO complete test
  it should "return the operations to be executed in cleanup" in {
    pending
  }
}
