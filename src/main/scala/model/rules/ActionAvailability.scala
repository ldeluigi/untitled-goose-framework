package model.rules

import model.actions.Action

trait ActionAvailability {
  def action: Action

  def allowed: Boolean

  def priority: Int
}

object ActionAvailability {

  implicit def fromTuple(t: (Boolean, Int, Action)): ActionAvailability =
    new {} with ActionAvailability {
      override def action: Action = t._3

      override def allowed: Boolean = t._1

      override def priority: Int = t._2
    }

  def apply(action: Action, priority: Int = 0, allowed: Boolean = true): ActionAvailability =
    (allowed, priority, action)
}
