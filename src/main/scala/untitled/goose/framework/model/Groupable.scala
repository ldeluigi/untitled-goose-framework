package untitled.goose.framework.model

/** An entity that can be grouped. That is,
 * string-defined groups can be assigned to it. */
trait Groupable {

  /** Checks if this entity belongs to a group. */
  def belongsTo(group: String): Boolean = groups.contains(group)

  /** Returns the set of groups this entity belongs to. */
  def groups: Seq[String]
}
