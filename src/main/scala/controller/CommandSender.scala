package controller

import model.actions.Action

trait CommandSender {
  def resolveAction(action: Action)

  def stopGame(): Unit
}
