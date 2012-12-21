package controllers

import play.api.libs.json.Json.toJson
import play.api.mvc._
import models.Task
import play.api.templates.Html
import play.api.libs.json.JsValue

object Tasks extends Controller with Secured {

  def index = IsAuthenticated {
    _ => implicit request =>
      Ok(views.html.tasks(Task.all()))
  }



  case class HtmlFragment(id: String)(f: String => Html) {

    lazy val asJson: JsValue = toJson(Map(
      "id" -> toJson(id),
      "html" -> toJson(f(id).body)
    ))
  }


  def tasks = IsAuthenticated {
    _ => implicit request => {
      val tasks = Task.all()
      Ok(toJson(Map(
        "fragments" -> Seq(
          HtmlFragment(ID.tasks.tasksTable)(views.html.fragments.tasksTable(_, tasks)).asJson,
          HtmlFragment(ID.tasks.numberOfTasksLabel)(views.html.fragments.numberOfTasksLabel(_, tasks)).asJson
        )
      )))
    }
  }


  def addTask = IsAuthenticatedJson {
    _ => implicit request =>
      (request.body \ "label").asOpt[String].map {
        label =>
          if (label.isEmpty || label.equals("")) {
            BadRequest("Label is required!")
          } else {
            Task.create(label)
            Ok("addTaskJson with label: " + label)
          }
      }.getOrElse {
        BadRequest("Missing parameter [label]")
      }
  }


  def deleteTask = IsAuthenticatedJson {
    _ => implicit request =>
      (request.body \ "id").asOpt[Long].map {
        id =>
          Task.delete(id)
          Ok("deleted Task with id:" + id)
      }.getOrElse {
        BadRequest("Missing parameter [id]")
      }
  }


}
