package net.garethrogers.framework

/**
 * Handles the templating for the framework
 */
import xml._
import xml.transform._

trait Templating
{
//    def replaceFeatures(dom: Node, features: List[String]): Node =
//    {
//        object replaceFeatures extends RewriteRule {
//            // The rule replaces any "features" element with a new
//            // features element we build for the occasion.
//            override def transform(n: Node): Seq[Node] = n match
//            {
//                // match on the "features" element
//                case Elem(prefix, "features", attribs, scope, _*) =>
//                    // XML literals again...
//
//                    // Can embed scala inside an XML literal: In this case,
//                    // apply an anonymous function over the list of features
//                    // in the original parameter list of the replaceFeatures
//                    // function. The func turns a feature name into a feature
//                    // node.
//                    <features>
//                        { features map { d => <feature id={d}/> } }
//                    </features>
//                // That which we cannot speak of, we must pass over
//                // in silence....
//                case other =>
//                    other
//            }
//
//        }
//        // Subclass a RuleTransformer (because it's abstract), handing
//        // it a vararg list of rules to use (in this case, just one).
//        object transform extends RuleTransformer(replaceFeatures)
//        // Do the transform. (A scala function's return value is the
//        // value of the last expression (and everything's an expression).
//        transform(xml)
//    }

    def normaliseXML(xml: String): Node = TagSoupFactoryAdapter.get.loadFile(xml)

    def surroundTemplate(root: String, xml: Node)=
    {
        val innerNode=xml \\ "_" filterNot(
            _.attribute("class")
                .map(_.text).getOrElse("").split(" ")
                .filter(_.startsWith("framework-surround:"))
                .isEmpty
        )
        if(innerNode.isEmpty)
        {
            xml
        }
        else
        {
            val classes=innerNode.head.attribute("class").get.text.split(" ")
            val outerFilename=classes.filter(_.startsWith("framework-surround:")).map(_.stripPrefix("framework-surround:")).head
            val newclasses=classes.filterNot(_.startsWith("framework-surround:"))
            val outerXML=normaliseXML(root+"/"+outerFilename+".html")

            val InsertIt=new RewriteRule
            {
                override def transform(n: Node): NodeSeq = n match
                {
                    case e: Elem if (e \ "@class").text.contains("framework-surroundpoint") =>
                        e match
                        {
                            case Elem(prefix, label, attribs, scope, _*) =>
                                Elem(prefix, label, attribs, scope, innerNode.head.child : _*)
                            case x => x
                        }
                    case x => x
                }
            }
            new RuleTransformer(InsertIt).transform(outerXML).head
        }
    }

    def processView(root: String, location: String): String=
    {
        val xml=normaliseXML(root+location)
        surroundTemplate(root, xml) toString
        //xml toString
    }
}

