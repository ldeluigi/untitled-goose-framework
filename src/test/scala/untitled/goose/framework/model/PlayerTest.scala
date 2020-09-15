package untitled.goose.framework.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.model.entities.runtime.Player

class PlayerTest extends AnyFlatSpec with Matchers {

  behavior of "PlayerTest"

  val name: String = "PlayerName"
  val player: Player = Player(name)

  it should "have a name" in {
    player.name should not be empty
    player.name should equal(name)
  }

  it should "have a history of happened player events" in {
    player.history should have size 0
  }

}
