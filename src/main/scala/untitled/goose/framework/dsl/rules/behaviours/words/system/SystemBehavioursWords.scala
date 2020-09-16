package untitled.goose.framework.dsl.rules.behaviours.words.system

import untitled.goose.framework.model.rules.behaviours._

trait SystemBehavioursWords {

  def Include: IncludeWord = new IncludeWord

  val MovementWithDice: BehaviourRule = MovementWithDiceBehaviour()
  val MultipleStep: BehaviourRule = MultipleStepBehaviour()
  val Teleport: BehaviourRule = TeleportBehaviour()
  val VictoryManager: BehaviourRule = VictoryBehaviour()
  val SkipTurnManager: BehaviourRule = SkipTurnBehaviour()


}
