package dsl

import model.actions.Action
import model.entities.board.Board
import model.rules.ActionRule
import model.rules.behaviours.BehaviourRule

object GameDataTree {

  var board: Option[Board] = None

  var actions: Set[Action] = Set()

  var actionRules: Set[ActionRule] = Set()

  var behaviours: Seq[BehaviourRule] = Seq()

}
