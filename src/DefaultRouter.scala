package net.garethrogers.framework

/**
 * The default router used by the application
 * Creates routes based on the contents of the views directory
 *
 * Any user defined router must inherit from this router
 */
import java.io.File
import scala.util.matching.Regex

trait DefaultRouter extends Templating
{
    var routesMap: Map[String, Map[String, Request => Response]]=Map.empty  //method -> (location -> function)

    def routes()={}

    def addToMap(method: String, url: String, function: Request => Response) =
    {
        this.routesMap+=(method -> (routesMap.getOrElse(method, Map[String, Request => Response]())+(url -> function)))
    }

    def setupRoutes(appRootDir: String)
    {
        val sep=File.separator
        val viewsLocation=sep+"src"+sep+"views"
        // default router will setup routes based on location in the directory
        val htmlFiles=recursiveListFiles(new File(appRootDir+viewsLocation), """.*.html""".r)
        htmlFiles.map{
            f =>
                val file=f.getCanonicalPath
                        .stripPrefix(appRootDir+viewsLocation+sep)
                        .replaceAllLiterally("""\""", """/""")
                addToMap("GET", "/"+file.split('.')(0), doSomething(appRootDir+viewsLocation, sep+file))
        }
        println(routesMap)
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
        val output=processView(appRootDir+url)
        new Response(List[(String, String)](), output)
    }
}
