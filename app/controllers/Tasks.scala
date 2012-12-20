package controllers

import play.api.libs.json.Json.toJson
import play.api.mvc._
import models.Task

object Tasks extends Controller with Secured {

  def index = IsAuthenticated {
    _ => implicit request =>
      Ok(views.html.tasks())
  }

  def tasks = IsAuthenticated {
    _ => implicit request =>
      Ok(toJson(Map(
        "tasks" -> Task.all().map {
          task =>
            toJson(Map(
              "id" -> toJson(task.id),
              "label" -> toJson(task.label)
            ))
        }
      )))
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
