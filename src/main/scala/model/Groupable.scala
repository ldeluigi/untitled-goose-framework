package model

/** Grouping utils. */
trait Groupable {

  /** Checks if a certain group is contained in the total set of groups.
   *
   * @param group the group to check
   * @return the value indicating is a group is contained
   */
  def belongsTo(group: String): Boolean = groups.contains(group)

  /** Total set of groups.
   *
   * @return the set of groups
   */
  def groups: Set[String]
}
