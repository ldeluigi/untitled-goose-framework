package untitled.goose.framework.dsl.rules.behaviours.nodes

import untitled.goose.framework.dsl.rules.behaviours.words.BehaviourNodeState.{BaseState, ConsumeSet, SaveSet}
import untitled.goose.framework.dsl.rules.behaviours.words.{AndWord, BehaviourNodeState, EventsWord}

case class BehaviourNode[State <: BehaviourNodeState]() {

  def consume(and: AndWord)(implicit ev: State =:= BaseState): BehaviourNode[ConsumeSet] = ???

  def save(events: EventsWord)(implicit ev: State =:= ConsumeSet): Unit = ???


  def save(and: AndWord)(implicit ev: State =:= BaseState): BehaviourNode[SaveSet] = ???

  def consume(events: EventsWord)(implicit ev: State =:= SaveSet): Unit = ???
}

