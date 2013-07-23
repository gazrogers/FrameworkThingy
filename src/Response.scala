/**
 * Represents a response to be returned from the application
 */
case class Response(headers: List[(String, String)], body: String)
{
    override def toString=
    {
        body
    }
}
