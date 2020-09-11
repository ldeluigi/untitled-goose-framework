package untitled.goose.framework.dsl.rules.behaviours.words

sealed trait BehaviourNodeState

object BehaviourNodeState {

  sealed trait BaseState extends BehaviourNodeState

  sealed trait ConsumeSet extends BehaviourNodeState

  sealed trait SaveSet extends BehaviourNodeState

}
