package untitled.goose.framework.dsl.dice.words

/** Used to complete dice related sentences. */
trait DiceWords {

  /** Enables "Create [item] ..." */
  val Create: CreateWord = new CreateWord

  /**
   * Creates a sequence with the sides of a dice with integer-typed faces.
   * Enables "... totalSides [number]".
   *
   * @param total the number of sides of the dice.
   * @return a sequence of integers, from 1 to total
   */
  def totalSides(total: Int): Seq[Int] = 1 to total

  /** Enables "... sides([side], [side], ...)" */
  def sides[T](values: T*): Seq[T] = values
}
