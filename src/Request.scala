/**
 * Created with IntelliJ IDEA.
 * User: garethrogers
 * Date: 16/07/2013
 * Time: 22:54
 * To change this template use File | Settings | File Templates.
 */
case class Request(method: String, url: String, httpVersion: (Int, Int), headers: List[(String, String)], body: String)
