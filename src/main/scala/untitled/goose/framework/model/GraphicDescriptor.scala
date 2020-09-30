package untitled.goose.framework.model

/**
 * An object which describes the graphical properties that the user wants the runtime to have.
 */
trait GraphicDescriptor {

  /** If present, the background colour. */
  def color: Option[Colour]

  /** If present, the path to the background image. */
  def imagePath: Option[String]

  override def toString: String = "Color: " + this.color.toString + " Path: " + this.imagePath.toString
}

object GraphicDescriptor {

  private class GraphicDescriptorImpl
  (val color: Option[Colour], val imagePath: Option[String])
    extends GraphicDescriptor

  /** Merges two graphic descriptors giving priority to the first one. */
  def merge(a: GraphicDescriptor, b: GraphicDescriptor): GraphicDescriptor = {
    GraphicDescriptor(
      if (a.color.isDefined) a.color else b.color,
      if (a.imagePath.isDefined) a.imagePath else b.imagePath
    )
  }

  /**
   * Factory method that creates a GraphicDescriptor with given specifications.
   *
   * @param colorOption     If specified, represents the background colour.
   * @param imagePathOption If specified, represents the path for the background image.
   * @return a new GraphicDescriptor.
   */
  def apply(colorOption: Option[Colour], imagePathOption: Option[String]): GraphicDescriptor =
    new GraphicDescriptorImpl(colorOption, imagePathOption)

  /** A factory which creates a new GraphicDescriptor if only a custom colour is specified. */
  def apply(specifiedColor: Colour): GraphicDescriptor = apply(Some(specifiedColor), None)

  /** A factory which creates a new GraphicDescriptor if only a custom resource imagePath is specified. */
  def apply(path: String): GraphicDescriptor = apply(None, Some(path))

  /** A factory which creates a new GraphicDescriptor if a custom colour, imagePath, stroke colour and tile's name are specified. */
  def apply(specifiedColor: Colour, path: String, strokeColor: Colour, tileName: String): GraphicDescriptor =
    apply(Some(specifiedColor), Some(path))

}
