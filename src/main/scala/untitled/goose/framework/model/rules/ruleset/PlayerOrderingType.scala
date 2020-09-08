package untitled.goose.framework.model.rules.ruleset

object PlayerOrderingType extends Enumeration {

  type PlayerOrderingType = Value
  val RandomEachTurn, FirstTurnRandomThenFixed, Fixed = Value
}
