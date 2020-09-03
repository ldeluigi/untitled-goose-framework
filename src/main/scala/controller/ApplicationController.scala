package controller

import controller.engine.vertx.GooseEngine
import model.actions.Action
import model.entities.DialogContent
import model.entities.runtime.{Game, GameState}
import model.events.GameEvent
import view.GameScene

import scala.concurrent.{Future, Promise}

case class ApplicationController(gameMatch: Game) extends ViewController with CommandSender {

  val engine: GooseEngine = GooseEngine(gameMatch, this)
  var gameScene: Option[GameScene] = None

  def setScene(gameScene: GameScene): Unit = {
    this.gameScene = Some(gameScene)
  }

  override def resolveAction(action: Action): Unit =
    engine.eventSink.accept(action.trigger(engine.currentMatch.currentState))

  override def update(state: GameState): Unit = {
    val clonedState: GameState = state.clone()
    gameScene.get.updateScene(clonedState, engine.currentMatch.availableActions)
  }

  override def close(): Unit = {
    stopGame()
    gameScene.get.close()
  }

  override def logAsyncEvent(event: GameEvent): Unit = gameScene.get.logEvent(event)


  override def showDialog(content: DialogContent): Future[GameEvent] = {
    val promise: Promise[GameEvent] = Promise()
    gameScene.get.showDialog(content, promise)
    promise.future
  }

  override def stopGame(): Unit = engine.stop()
}
