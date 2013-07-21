/**
 * Created with IntelliJ IDEA.
 * User: garethrogers
 * Date: 16/07/2013
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */
import java.io.File
import scala.util.matching.Regex

trait DefaultRouter
{
    var routesMap: Map[String, Map[String, Request => Response]]=Map.empty  //method -> (location -> function)

    def routes()={}

    def setupRoutes(appRootDir: String)=
    {
        // default router will setup routes based on location in the directory
        println("Default router")
        val htmlfiles=recursiveListFiles(new File(appRootDir+"/src/views"), """.*.html""".r)
        htmlfiles.map(f => addToMap("GET", f.getName.split('.')(0), doSomething))

        println("RoutesMap:")
        println(routesMap)
        println()
    }

    def addToMap(method: String, url: String, function: Request => Response) =
    {
        this.routesMap+=(method -> (routesMap.getOrElse(method, Map[String, Request => Response]())+(url -> function)))
    }

    def recursiveListFiles(f: File, r: Regex): Array[File] =
    {
        val these = f.listFiles
        if(these!=null)
        {
            val good = these.filter(f => r.findFirstIn(f.getName).isDefined)
            good ++ these.filter(_.isDirectory).flatMap(recursiveListFiles(_,r))
        }
        else
        {
            Array[File]()
        }
    }

    def doSomething(req: Request) =
    {
        new Response(List[(String, String)](), "")
    }
}
