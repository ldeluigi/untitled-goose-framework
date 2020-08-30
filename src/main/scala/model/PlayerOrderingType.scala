package model

object PlayerOrderingType extends Enumeration {

  type PlayerOrderingType = Value
  val FullRandom, RandomOrder, UserDefinedOrder = Value
}
