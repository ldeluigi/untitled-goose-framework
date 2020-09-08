package untitled.goose.framework.model.rules.ruleset

/** Types of player ordering. */
object PlayerOrderingType extends Enumeration {

  type PlayerOrderingType = Value

  val RandomEachTurn,
  FirstTurnRandomThenFixed,
  Fixed = Value
}
