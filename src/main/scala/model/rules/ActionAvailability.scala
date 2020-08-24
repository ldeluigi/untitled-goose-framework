package model.rules

import model.actions.Action

/** Models the concepf of availability for actions. */
trait ActionAvailability {

  /** Gets the action.
   *
   * @return the action itself
   */
  def action: Action

  /** Permission to perform an action.
   *
   * @return the permission value
   */
  def allowed: Boolean

  /** Action priority.
   *
   * @return the action priority
   */
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

  /** Implicit conversion for a ActionAvailability object-
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

  /** A factory to create an ActionAvailability object.
   *
   * @param action   the specified action
   * @param priority the action's priority
   * @param allowed  permission to perform the action
   * @return the newly created ActionAvailability object
   */
  def apply(action: Action, priority: Int = 0, allowed: Boolean = true): ActionAvailability =
    (allowed, priority, action)

  def unapply(arg: ActionAvailability): Option[(Boolean, Int, Action)] = Some(arg.allowed, arg.priority, arg.action)
}
