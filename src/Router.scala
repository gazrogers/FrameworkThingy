package net.garethrogers.framework

/**
 * Alternative router
 * Allows the user to define their own routes using get and post methods
 */
trait Router extends DefaultRouter
{
    override def setupRoutes(appRootDir: String)=
    {
        // this router will call user defined routes method
        routes()
        println(routesMap)
    }

    def get(url: String)(function: Request => Response) =
    {
        addToMap("GET", url, function)
    }

    def post(url: String)(function: Request => Response) =
    {
        addToMap("POST", url, function)
    }
}
