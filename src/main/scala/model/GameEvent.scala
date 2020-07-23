package model

trait GameEvent extends Serializable {

  def name: String

  def group: List[String]

}
