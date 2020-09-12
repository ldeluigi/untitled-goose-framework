package untitled.goose.framework.dsl.rules.behaviours.nodes

import untitled.goose.framework.dsl.rules.behaviours.words.BehaviourNodeState.{BaseState, BothSet, NotBaseState, SaveOrConsumeSet}
import untitled.goose.framework.dsl.rules.behaviours.words.{BehaviourNodeState, SaveOrConsumeWord}

case class BehaviourNode[State <: BehaviourNodeState]() {

  def andThen(saveOrConsume: SaveOrConsumeWord)(implicit ev: State =:= BaseState): BehaviourNode[SaveOrConsumeSet] = ???

  def and(saveOrConsume: SaveOrConsumeWord)(implicit ev: State =:= SaveOrConsumeSet): BehaviourNode[BothSet] = ???

  def allEvents[T >: State <: NotBaseState]: Unit = {}


}

