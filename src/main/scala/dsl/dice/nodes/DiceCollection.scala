package dsl.dice.nodes

import model.entities.Dice.{Dice, MovementDice}

trait DiceCollection {

  def isDiceDefined(name: String): Boolean

  def isMovementDice(name: String): Boolean

  def getDice(name: String): Dice[Any]

  def getMovementDice(name: String): MovementDice

}
