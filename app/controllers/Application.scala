package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._
import models.Task
import java.util.UUID

object Application extends Controller with Secured{


  def javascriptRoutes = Action {
    implicit request =>
      import play.api.Routes
      Ok(
        Routes.javascriptRouter("jsRoutes")(
          routes.javascript.Tasks.addTask,
          routes.javascript.Tasks.deleteTask,
          routes.javascript.Events.events
        )
      ).as("text/javascript")
  }


  //Authentication
  val loginForm = Form(
    tuple(
      "username" -> text,
      "password" -> text
    ) verifying("Invalid username or password", result => result match {
      case (username, password) => check(username, password)
    })
  )

  def check(username: String, password: String) = {
    (username == "admin" && password == "admin")
  }

  def login = Action {
    implicit request =>
      username(request).map(_ => Redirect(routes.Tasks.index)).getOrElse(Ok(html.login(loginForm)))
  }

  def authenticate = Action {
    implicit request =>
      loginForm.bindFromRequest.fold(
        formWithErrors => BadRequest(html.login(formWithErrors)),
        user => Redirect(routes.Tasks.index).withSession(Security.username -> user._1, Secured.sessionId -> UUID.randomUUID().toString)
      )
  }

  def logout = Action {
    Redirect(routes.Application.login).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }
}
