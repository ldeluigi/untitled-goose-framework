package model.rules

import model.actions.Action

trait ActionAvailability {
  def action: Action

  def allowed: Boolean

  def priority: Int
}
