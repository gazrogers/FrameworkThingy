package net.garethrogers.framework

/**
 * Represents an HTTP request passed into the application
 */
case class Request(method: String, url: String, httpVersion: (Int, Int), headers: List[(String, String)], body: String)
