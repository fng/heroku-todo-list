package controllers

import play.api.libs.json.Json.toJson
import play.api.mvc._
import models.Task

object Tasks extends Controller with Secured {

  def index = IsAuthenticated {
    _ => implicit request =>
      Ok(views.html.tasks(Task.all()))
  }




  def buildTasksFragmentContainer: HtmlFragmentContainer = {
    val tasks = Task.all()
    HtmlFragmentContainer(
      HtmlFragment(ID.tasks.tasksTable)(views.html.fragments.tasksTable(_, tasks)),
      HtmlFragment(ID.tasks.numberOfTasksLabel)(views.html.fragments.numberOfTasksLabel(_, tasks))
    )
  }

  def addTask = IsAuthenticated(parse.json) {
    _ => implicit request =>
      (request.body \ "label").asOpt[String].map {
        label =>
          if (label.isEmpty || label.equals("")) {
            BadRequest("Label is required!")
          } else {
            Task.create(label)
            Events.queue.add(buildTasksFragmentContainer)
            Ok("addTaskJson with label: " + label)
          }
      }.getOrElse {
        BadRequest("Missing parameter [label]")
      }
  }


  def deleteTask = IsAuthenticated(parse.json) {
    _ => implicit request =>
      (request.body \ "id").asOpt[Long].map {
        id =>
          Task.delete(id)
          Events.queue.add(buildTasksFragmentContainer)
          Ok("deleted Task with id:" + id)
      }.getOrElse {
        BadRequest("Missing parameter [id]")
      }
  }


}
