package untitled.goose.framework.model.rules.ruleset

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.actions.{Action, StepForwardAction}
import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime._
import untitled.goose.framework.model.rules.actionrules.AlwaysActionRule.{AlwaysNegatedActionRule, AlwaysPermittedActionRule}

class PriorityRuleSetTest extends AnyFlatSpec with BeforeAndAfterEach with Matchers {

  val t1: Tile = Tile(TileDefinition(1))
  val t2: Tile = Tile(TileDefinition(2))
  val tileSet: Set[Tile] = Set(t1, t2)
  val firstPosition: Set[Tile] => Position = tiles => Position(tiles.toList.sorted.take(1).head)
  val playersRange: Range = 1 to 10
  val p1: Player = Player("P1")
  val p2: Player = Player("P2")
  val ordering: PlayerOrdering = PlayerOrdering.givenOrder(Seq(p1, p2))

  var gameMatch: Game = MatchMock.default
  val gameMutableState: MutableGameState = gameMatch.currentState

  var ruleSet: PriorityRuleSet = PriorityRuleSet(firstPosition, ordering, playersRange)

  override protected def beforeEach(): Unit = {
    ruleSet = PriorityRuleSet(firstPosition, ordering, playersRange)
  }

  val myAction: Action = StepForwardAction()


  "A priority based rule set" should "define the starting position from a given set of tiles" in {
    ruleSet.startPosition(tileSet) should equal(Position(t1))
  }

  it should "define the first player to play from a given set" in {
    ruleSet.first(Set(p1, p2)) should equal(p1)
  }

  it should "define the next player given the current one" in {
    ruleSet.nextPlayer(p1, Set(p1, p2)) should equal(p2)
  }

  it should "permit an action if the priority of the negation is lower than the permission" in {
    val negation = AlwaysNegatedActionRule(1, myAction)
    val permission = AlwaysPermittedActionRule(2, myAction)
    val ruleSet = PriorityRuleSet(admissiblePlayers = 1 to 10, actionRules = Set(negation, permission))
    ruleSet.actions(null) should contain(myAction)
  }

  it should "negate the action if the priority of the permission is lower than the negation" in {
    val negation = AlwaysNegatedActionRule(2, myAction)
    val permission = AlwaysPermittedActionRule(1, myAction)
    val ruleSet = PriorityRuleSet(admissiblePlayers = 1 to 10, actionRules = Set(negation, permission))
    ruleSet.actions(null) should not contain myAction
  }
}
