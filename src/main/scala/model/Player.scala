package model

trait Player {

}

object Player {
  def apply() = PlayerImpl()
}

case class PlayerImpl() {

}
