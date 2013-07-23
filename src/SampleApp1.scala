/**
 * Classes for testing the framework as I develop it
 */

class SampleApp1 extends Application
{
}

class SampleApp2 extends Application with Router
{
    override def routes()=
    {
        get("/anotherpage") {req: Request =>
            println("url1 function (GET)")
            new Response(List[(String, String)](), "")
        }

        get("/index") {req: Request =>
            println("url2 function (GET)")
            new Response(List[(String, String)](), "")
        }

        get("/stuff/things") {req: Request =>
            println("url3 function (GET)")
            new Response(List[(String, String)](), "")
        }
    }
}

object testApplication extends App
{
    val app1=new SampleApp1()
    val app2=new SampleApp2()
    val testRequest="GET /anotherpage HTTP/1.1\r\n\r\n"
    val testRequest2="GET /index HTTP/1.1\r\nheader1: value1\r\nheader2: value2\r\n\r\n"
    val testRequest3="GET /stuff/things HTTP/1.1\r\nheader1: value1\r\nheader2: value2\r\n\r\nBody text"

    println(app1.generateResponse(testRequest))
    println()
    println(app1.generateResponse(testRequest2))
    println()
    println(app1.generateResponse(testRequest3))
    println()

    println(app2.generateResponse(testRequest))
    println()
    println(app2.generateResponse(testRequest2))
    println()
    println(app2.generateResponse(testRequest3))
    println()
}
