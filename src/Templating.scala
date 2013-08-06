package net.garethrogers.framework

/**
 * Handles the templating for the framework
 */
import xml._
import xml.transform._

trait Templating
{
    def normaliseXML(xml: String): Node = TagSoupFactoryAdapter.get.loadFile(xml)

    def surroundTemplate(root: String, xml: Node)=
    {
        val innerNode=xml \\ "_" filterNot(_.attribute("data-frameworksurround").isEmpty)
        if(innerNode.isEmpty)
        {
            xml
        }
        else
        {
            val outerFilename=innerNode.head.attribute("data-frameworksurround").get.text
            val templateAttribs=innerNode.head.attributes
            val templateTitle=(xml \\ "title").headOption.map(_.child).getOrElse(Seq[Node]())
            val templateStyle=(xml \\ "style").headOption.map(_.child).getOrElse(Seq[Node]())
            val templateScript=(xml \\ "script")
            val outerXML=normaliseXML(root+"/"+outerFilename+".html")

            val InsertIt=new RewriteRule
            {
                override def transform(n: Node): NodeSeq = n match
                {
                    case e: Elem if !(e \ "@data-frameworksurroundpoint").isEmpty =>
                        e match
                        {
                            case Elem(prefix, label, attribs, scope, _*) =>
                                Elem(
                                    prefix,
                                    label,
                                    templateAttribs.remove("data-frameworksurround"),
                                    scope,
                                    innerNode.head.child : _*
                                )
                            case x => x
                        }
                    case x => x
                }
            }
            val mergeHeadTags=new RewriteRule
            {
                override def transform(n: Node): NodeSeq = n match
                {
                    case Elem(prefix, "title", attribs, scope, children @ _*) =>
                        Elem(prefix, "title", attribs, scope, templateTitle:_*)
                    case Elem(prefix, "style", attribs, scope, children @ _*) =>
                        Elem(prefix, "style", attribs, scope, children++templateStyle:_*)
                    case Elem(prefix, "head", attribs, scope, children @ _*) =>
                        Elem(prefix, "head", attribs, scope, children++templateScript:_*)
                    case x => x
                }
            }
            val y=new RuleTransformer(InsertIt).transform(outerXML).head
            new RuleTransformer(mergeHeadTags).transform(y).head
        }
    }

    def processView(root: String, location: String): String=
    {
        val xml=normaliseXML(root+location)
        surroundTemplate(root, xml) toString
        //xml toString
    }
}

