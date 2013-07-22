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

    def setupRoutes(appRootDir: String)
    {
        val sep=File.separator
        val viewsLocation=sep+"src"+sep+"views"
        // default router will setup routes based on location in the directory
        val htmlFiles=recursiveListFiles(new File(appRootDir+viewsLocation), """.*.html""".r)
        htmlFiles.map(f => addToMap("GET", "/"+f.getName.split('.')(0), doSomething(appRootDir+viewsLocation, sep+f.getName)))
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

    def doSomething(appRootDir:String, url: String)(req: Request) =
    {
        println("File requested: "+url)
        val source = scala.io.Source.fromFile(appRootDir+url)

        new Response(List[(String, String)](), source.getLines mkString "\n")
    }
}
