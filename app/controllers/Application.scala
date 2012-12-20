package controllers

import play.api.mvc._

object Application extends Controller {


  def index = Action {
    Ok(views.html.index())
  }


  def javascriptRoutes = Action {
    implicit request =>
      import play.api.Routes
      Ok(
        Routes.javascriptRouter("jsRoutes")(
          routes.javascript.Tasks.tasks,
          routes.javascript.Tasks.addTask,
          routes.javascript.Tasks.deleteTask
        )
      ).as("text/javascript")
  }
}
