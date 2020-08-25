package dsl

import dsl.board.properties.{BoardPropertyChanger, DispositionProperty, TileNumProperty}

trait HasPropertyBuilder {

  def has(prop: TileNumProperty): BoardPropertyChanger = ???

  def has(prop: DispositionProperty): BoardPropertyChanger = ???

}
