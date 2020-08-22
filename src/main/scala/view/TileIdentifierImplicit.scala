package view

import model.TileIdentifier
import model.TileIdentifier.Group
import view.board.GraphicDescriptor

object TileIdentifierImplicit {

    /** An implicit mapping from a 2-entry tuple containing the tile's number and its properties, to a map containing the tile's number and the its properties. */
    implicit def numberToIdentifier(tuple: Tuple2[Int, GraphicDescriptor]): (TileIdentifier, GraphicDescriptor) = (TileIdentifier(tuple._1), tuple._2)

    /** An implicit mapping from a 2-entry tuple containing the tile's name and its properties, to a map containing the tile's name and the its properties. */
    implicit def nameToIdentifier(tuple: Tuple2[String, GraphicDescriptor]): (TileIdentifier, GraphicDescriptor) = (TileIdentifier(tuple._1), tuple._2)

    /** An implicit mapping from a 2-entry tuple containing the tile's group and its properties, to a map containing the tile's group and the its properties. */
    implicit def groupToIdentifier(tuple: Tuple2[Group, GraphicDescriptor]): (TileIdentifier, GraphicDescriptor) = (TileIdentifier(tuple._1), tuple._2)
}
