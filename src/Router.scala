/**
 * Created with IntelliJ IDEA.
 * User: garethrogers
 * Date: 16/07/2013
 * Time: 20:11
 * To change this template use File | Settings | File Templates.
 */
trait Router extends DefaultRouter
{
    override def setupRoutes()=
    {
        // this router will call user defined routes method
        routes()
    }

    def get(url: String)(function: Request => Response) =
    {
        addToMap("GET", url, function)
    }

    def post(url: String)(function: Request => Response) =
    {
        addToMap("POST", url, function)
    }

    def addToMap(method: String, url: String, function: Request => Response) =
    {
        this.routesMap+=(method -> (routesMap.getOrElse(method, Map[String, Request => Response]())+(url -> function)))
    }
}
