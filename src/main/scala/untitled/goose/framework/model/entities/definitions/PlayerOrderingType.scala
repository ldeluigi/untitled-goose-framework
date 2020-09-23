package untitled.goose.framework.model.entities.definitions

/** Types of player ordering. */
object PlayerOrderingType extends Enumeration {

  type PlayerOrderingType = Value

  val RandomEachTurn,
  FirstTurnRandomThenFixed,
  Fixed = Value
}
