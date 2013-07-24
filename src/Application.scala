package net.garethrogers.framework

/**
 * The trait that must be mixed into every application using the framework
 */
trait Application extends DefaultRouter
{
    val appLocation=System.getProperty("user.dir")

    def generateResponse(request:String): String =
    {
        val requestParts="""(?s)^(.*?)(\r\n.*)?\r\n\r\n(.*)?$""".r
        val requestLineParts="""^(.*) (.*) HTTP/(\d)\.(\d)""".r
        val requestParts(requestLine, headers, body)=request
        val requestLineParts(method, requestUrl, maj, min)=requestLine
        val headerList:List[(String, String)]=
            if(headers!=null && !headers.isEmpty)
                headers.drop(2).split("""\r\n""").map(_.split(""":\s*""")).map(x => (x.head, x.last)).toList
            else
                List[(String, String)]()
        val httpVersion=(maj.toInt,min.toInt)
        "Method: "+method+"\nURL: "+requestUrl+"\nHTTP version: "+httpVersion+"\nHeaders: ("+headerList.mkString(", ")+")\nBody: "+body

        routesMap("GET")(requestUrl)(new Request(method, requestUrl, httpVersion, headerList, body)).toString
    }

    setupRoutes(appLocation)
}
