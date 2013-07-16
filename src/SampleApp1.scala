/**
 * Created with IntelliJ IDEA.
 * User: garethrogers
 * Date: 16/07/2013
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */

class SampleApp1 extends Application
{
    //var testfunc=(x: Request)=>{new Response(List[(String, String)](), "")}
    routesMap=Map(("three", Map(("one",(x:Request)=>new Response(List[(String, String)](), "")))))
}

class SampleApp2 extends Application with Router
{
    override def routes=
    {
        get("url1") {req: Request =>
            println("url1 function (GET)")
            new Response(List[(String, String)](), "")
        }

        get("url2") {req: Request =>
            println("url2 function (GET)")
            new Response(List[(String, String)](), "")
        }

        post("url1") {req: Request =>
            println("url1 function (POST)")
            new Response(List[(String, String)](), "")
        }

        println("RoutesMap:")
        println(routesMap)
        println()
    }
}

object testApplication extends App
{
    val app1=new SampleApp1()
    val app2=new SampleApp2()
    val testRequest="GET /pub/WWW/TheProject.html HTTP/1.1\r\n\r\n"
    val testRequest2="GET /pub/WWW/TheProject.html HTTP/1.1\r\nheader1: value1\r\nheader2: value2\r\n\r\n"
    val testRequest3="GET /pub/WWW/TheProject.html HTTP/1.1\r\nheader1: value1\r\nheader2: value2\r\n\r\nBody text"

    println(app1.generateResponse(testRequest))
    println
    println(app1.generateResponse(testRequest2))
    println
    println(app1.generateResponse(testRequest3))
    println

    println(app2.generateResponse(testRequest))
    println
    println(app2.generateResponse(testRequest2))
    println
    println(app2.generateResponse(testRequest3))
    println
}
