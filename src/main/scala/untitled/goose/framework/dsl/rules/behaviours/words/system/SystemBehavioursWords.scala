package untitled.goose.framework.dsl.rules.behaviours.words.system

import untitled.goose.framework.model.rules.behaviours._

/** Used to offer system behaviours to programmer. */
trait SystemBehavioursWords {

  /** Enables "Include ..." */
  def Include: IncludeWord = new IncludeWord

  /** Manages conversion from movement dice rolls to movement events. */
  val MovementWithDice: BehaviourRule = MovementWithDiceBehaviour()

  /** Manages movement events actuating movement of pieces on the board. */
  val MultipleStep: BehaviourRule = MultipleStepBehaviour()

  /** Actuates teleportation when a teleport event is triggered. */
  val Teleport: BehaviourRule = TeleportBehaviour()

  /** Manages victory events, closing the game if found. */
  val VictoryManager: BehaviourRule = VictoryBehaviour()

  /** Manages turn skip events, negating actions to the player who must skip its turn. */
  val SkipTurnManager: BehaviourRule = SkipTurnBehaviour()

}
