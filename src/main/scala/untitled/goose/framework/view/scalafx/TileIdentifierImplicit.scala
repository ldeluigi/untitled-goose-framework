package untitled.goose.framework.view.scalafx

import untitled.goose.framework.model.GraphicDescriptor
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.model.entities.definitions.TileIdentifier.Group

/** Defines implicit conversions for tile identifiers -> graphic descriptors. */
object TileIdentifierImplicit {

  /** An implicit mapping from a 2-entry tuple containing the tile's number and its properties, to a map containing the tile's number and the its properties. */
  implicit def numberToIdentifier(tuple: (Int, GraphicDescriptor)): (TileIdentifier, GraphicDescriptor) = (TileIdentifier(tuple._1), tuple._2)

  /** An implicit mapping from a 2-entry tuple containing the tile's name and its properties, to a map containing the tile's name and the its properties. */
  implicit def nameToIdentifier(tuple: (String, GraphicDescriptor)): (TileIdentifier, GraphicDescriptor) = (TileIdentifier(tuple._1), tuple._2)

  /** An implicit mapping from a 2-entry tuple containing the tile's group and its properties, to a map containing the tile's group and the its properties. */
  implicit def groupToIdentifier(tuple: (Group, GraphicDescriptor)): (TileIdentifier, GraphicDescriptor) = (TileIdentifier(tuple._1), tuple._2)
}
