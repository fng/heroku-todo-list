package controllers

import play.api._
import libs.json.JsValue
import play.api.mvc._
import play.api.mvc.BodyParsers.parse

/**
 * Provide security features
 */
trait Secured {
  this: Controller =>

  /**
   * Retrieve the connected user email.
   */
  def username(request: RequestHeader) = request.session.get(Security.username)


  def sessionId(request: RequestHeader) = request.session.get(Secured.sessionId)

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.login)

  // --

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result): Action[(Action[AnyContent], AnyContent)] = IsAuthenticated(BodyParsers.parse.anyContent)(f)

  def IsAuthenticated[A](parser: BodyParser[A])(f: => String => Request[A] => Result): Action[(Action[A], A)] = Security.Authenticated(username, onUnauthorized) {
    user =>
      Action(parser)(
        request => {
          sessionId(request) match {
            case Some(id) =>
              f(user)(request)
            case None => Unauthorized("No Session id!").withNewSession
          }
        }
      )
  }

}

object Secured {
  val sessionId: String = "session-id"
}