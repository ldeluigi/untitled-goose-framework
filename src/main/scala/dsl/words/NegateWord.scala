package dsl.words

class NegateWord {
  def apply(to: ToWord): NegateWord = new NegateWord()

  def use(name: String): NamedAction = ???
}
