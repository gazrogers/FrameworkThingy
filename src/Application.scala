/**
 * Created with IntelliJ IDEA.
 * User: garethrogers
 * Date: 14/07/2013
 * Time: 19:41
 * To change this template use File | Settings | File Templates.
 */
trait Application extends DefaultRouter
{
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
    }

    setupRoutes()
}
