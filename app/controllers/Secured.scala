package controllers

import play.api._
import libs.json.JsValue
import play.api.mvc._
import play.api.mvc.BodyParsers.parse

/**
 * Provide security features
 */
trait Secured {

  /**
   * Retrieve the connected user email.
   */
  def username(request: RequestHeader) = request.session.get(Security.username)

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.login)

  // --

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) {
    user =>
      Action(request => f(user)(request))
  }


  def IsAuthenticatedJson(f: => String => Request[JsValue] => Result) = Security.Authenticated(username, onUnauthorized) {
    user =>
      Action(parse.json) {
        request => f(user)(request)
      }
  }

}