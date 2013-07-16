/**
 * Created with IntelliJ IDEA.
 * User: garethrogers
 * Date: 16/07/2013
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */
trait DefaultRouter
{
    var routesMap: Map[String, Map[String, Request => Response]]=Map.empty  //method -> (location -> function)

    def routes()={}

    def setupRoutes()=
    {
        // default router will setup routes based on location in the directory
        println("Default router")
    }
}
