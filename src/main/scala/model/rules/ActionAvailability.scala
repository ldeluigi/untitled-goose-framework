package model.rules

import model.actions.Action

trait ActionAvailability {
  def action: Action

  def allowed: Boolean

  def priority: Int

  override def equals(obj: Any): Boolean =
    obj match {
      case (al, pr, ac) =>
        action.equals(ac) && pr == priority && al == allowed
      case _ => false
    }

  override def toString: String = ActionAvailability.unapply(this).get.toString()
}

object ActionAvailability {

  implicit def fromTuple(t: (Boolean, Int, Action)): ActionAvailability =
    new ActionAvailability {
      override def action: Action = t._3

      override def allowed: Boolean = t._1

      override def priority: Int = t._2
    }

  def apply(action: Action, priority: Int = 0, allowed: Boolean = true): ActionAvailability =
    (allowed, priority, action)

  def unapply(arg: ActionAvailability): Option[(Boolean, Int, Action)] = Some(arg.allowed, arg.priority, arg.action)
}
