package model

trait Groupable {

  def belongsTo(group: String): Boolean = groups.contains(group)

  def groups: Set[String]
}
