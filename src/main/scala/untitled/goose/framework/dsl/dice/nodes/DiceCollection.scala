package untitled.goose.framework.dsl.dice.nodes

import untitled.goose.framework.model.entities.Dice
import untitled.goose.framework.model.entities.Dice.MovementDice

/**
 * This trait represent a collection of Dice and it can return a properly defined Dice by its name.
 */
trait DiceCollection {

  def isDiceDefined(name: String): Boolean

  def isMovementDice(name: String): Boolean

  def getDice(name: String): Dice[_]

  def getMovementDice(name: String): MovementDice

  def add(diceNode: DiceNode[_]): Unit
}
