package untitled.goose.framework.model.rules.actionrules

import untitled.goose.framework.model.actions.Action

/** Represents the availability of an action to a player. */
trait ActionAvailability {

  /** The action. */
  def action: Action

  /** True if the action should be available to the player, false if
   * it should be negated.
   */
  def allowed: Boolean

  /** The priority of this action availability. */
  def priority: Int

  override def equals(obj: Any): Boolean =
    obj match {
      case a: ActionAvailability =>
        action == a.action && a.priority == priority && a.allowed == allowed
      case _ => false
    }

  override def toString: String = ActionAvailability.unapply(this).get.toString()
}

object ActionAvailability {

  /** Implicit conversion for an ActionAvailability object.
   *
   * @param tuple a tuple containing permission and priority of a certain action
   * @return the ActionAvailability from the tuple
   */
  implicit def fromTuple(tuple: (Boolean, Int, Action)): ActionAvailability =
    new ActionAvailability {
      override def action: Action = tuple._3

      override def allowed: Boolean = tuple._1

      override def priority: Int = tuple._2
    }

  /** Factory method to build an action availability. */
  def apply(action: Action, priority: Int = 0, allowed: Boolean = true): ActionAvailability =
    (allowed, priority, action)

  /** Unapply method to extract the tuple from an action availability. */
  def unapply(arg: ActionAvailability): Option[(Boolean, Int, Action)] = Some(arg.allowed, arg.priority, arg.action)
}
