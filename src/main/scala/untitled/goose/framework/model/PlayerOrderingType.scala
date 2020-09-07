package untitled.goose.framework.model

object PlayerOrderingType extends Enumeration {

  type PlayerOrderingType = Value
  val RandomEachTurn, FirstTurnRandomThenFixed, Fixed = Value
}
