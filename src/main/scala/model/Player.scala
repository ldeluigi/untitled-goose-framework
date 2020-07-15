package model

trait Player {

}

object Player {
  def apply(): Player = PlayerImpl()
}

case class PlayerImpl() extends Player {

}
