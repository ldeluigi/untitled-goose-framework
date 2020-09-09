package untitled.goose.framework.dsl.dice.nodes

import untitled.goose.framework.model.entities.Dice
import untitled.goose.framework.model.entities.Dice.MovementDice

trait DiceCollection {

  def isDiceDefined(name: String): Boolean

  def isMovementDice(name: String): Boolean

  def getDice(name: String): Dice[Any]

  def getMovementDice(name: String): MovementDice
}