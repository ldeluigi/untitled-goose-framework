package untitled.goose.framework.view.scene.controller

import untitled.goose.framework.controller.ViewController
import untitled.goose.framework.controller.engine.GooseEngine
import untitled.goose.framework.controller.engine.vertx.VertxGooseEngine
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.special.ActionEvent
import untitled.goose.framework.view.InputManager
import untitled.goose.framework.view.scene.GameScene

import scala.concurrent.{Future, Promise}

/**
 * An application controller is a scene oriented view controller that manages
 * the engine, user input and a game scene.
 */
trait ApplicationController extends ViewController with InputManager with GameSceneManager

object ApplicationController {

  /**
   * This implementation of an ApplicationController delegates to the scene the
   * responsibility to draw/print updates received from the Engine.
   *
   * @param game the game definition to be passed to the engine.
   */
  case class ApplicationControllerImpl(game: Game) extends ApplicationController {

    private val engine: GooseEngine = VertxGooseEngine(game, this)
    private var gameScene: Option[GameScene] = None

    override def setGameScene(gameScene: GameScene): Unit = {
      this.gameScene = Some(gameScene)
      engine.callViewUpdate()
    }

    override def resolveAction(action: Action): Unit =
      engine.eventSink.accept(ActionEvent(action))

    override def update(state: GameState, availableActions: Set[Action]): Unit = {
      gameScene.get.updateScene(state, availableActions)
    }

    override def close(): Unit = {
      stopGame()
      gameScene.get.close()
    }

    override def showDialog(content: DialogContent): Future[GameEvent] = {
      val promise: Promise[GameEvent] = Promise()
      gameScene.get.showDialog(content, promise)
      promise.future
    }

    override def stopGame(): Unit = engine.stop()
  }

}
