package net.garethrogers.framework

/**
 * Handles the templating for the framework
 */

trait Templating
{
    def processView(location: String)=
    {
        val xml=TagSoupFactoryAdapter.get.loadFile(location)
        xml.toString
    }
}
