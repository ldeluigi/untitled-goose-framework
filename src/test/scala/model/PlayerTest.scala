package model

import org.scalatest.flatspec.AnyFlatSpec

class PlayerTest extends AnyFlatSpec {

  val name: String = "PlayerName"
  val player: Player = Player(name)

  "A player" should "have a name" in {
    assert(player.name.nonEmpty && player.name.equals(name))
  }

}
