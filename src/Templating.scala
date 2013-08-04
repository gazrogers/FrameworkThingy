package net.garethrogers.framework

/**
 * Handles the templating for the framework
 */
import xml._

trait Templating
{
    def normaliseXML(xml: String): Node = TagSoupFactoryAdapter.get.loadFile(xml)

    def hasClass(node: Node)=node.attribute("class").filter(_=="")

    def doSurround(xml: Node): Node =
    {
        val surroundNode=xml \\ "_" filter(_.attribute("class").filter(_.text.contains("framework:surround:")).isDefined)
        if(!surroundNode.isEmpty)
        {
            val outer=surroundNode.head.attribute("class")
        }
        surroundNode.headOption.getOrElse(xml)
    }

    def surroundWith(inner: Node, outer: Node): Node =
    {
        inner
    }

    def processView(location: String)=
    {
        val xml=doSurround(normaliseXML(location))
        xml toString
    }
}
