package untitled.goose.framework.dsl.rules.behaviours.words

sealed trait BehaviourNodeState

object BehaviourNodeState {

  sealed trait BaseState extends BehaviourNodeState

  sealed trait NotBaseState extends BehaviourNodeState

  sealed trait SaveOrConsumeSet extends NotBaseState

  sealed trait BothSet extends NotBaseState


}
