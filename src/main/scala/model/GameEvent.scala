package model

trait GameEvent {

  def persistent : Boolean

  def name : String

  def group : List[String]

}
