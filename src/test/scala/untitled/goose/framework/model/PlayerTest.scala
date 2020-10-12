package untitled.goose.framework.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.model.entities.runtime.Player
import untitled.goose.framework.model.entities.runtime.Player.PlayerImpl
import untitled.goose.framework.model.entities.runtime.PlayerDefinition.PlayerDefinitionImpl

class PlayerTest extends AnyFlatSpec with Matchers {

  behavior of "PlayerTest"

  val name: String = "PlayerName"
  val player: Player = PlayerImpl(PlayerDefinitionImpl(name), Seq())

  it should "have a name" in {
    player.definition.name should not be empty
    player.definition.name should equal(name)
  }

  it should "have a history of happened player events" in {
    player.history should have size 0
  }

}
