package net.garethrogers.framework

/**
 * HTML parser
 */
import xml.{Elem, XML}
import xml.factory.XMLLoader
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl

object TagSoupFactoryAdapter
{
    private val factory=new SAXFactoryImpl()

    def get(): XMLLoader[Elem]=
    {
        XML.withSAXParser(factory.newSAXParser())
    }
}
