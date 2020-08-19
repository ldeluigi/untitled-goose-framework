package view

import model.TileIdentifier
import model.TileIdentifier.Group
import view.board.GraphicDescriptor

object TileIdentifierImplicit {
    implicit def numberToIdentifier(tuple: Tuple2[Int, GraphicDescriptor]): (TileIdentifier, GraphicDescriptor) = (TileIdentifier(tuple._1), tuple._2)

    implicit def nameToIdentifier(tuple: Tuple2[String, GraphicDescriptor]): (TileIdentifier, GraphicDescriptor) = (TileIdentifier(tuple._1), tuple._2)

    implicit def groupToIdentifier(tuple: Tuple2[Group, GraphicDescriptor]): (TileIdentifier, GraphicDescriptor) = (TileIdentifier(tuple._1), tuple._2)
}
