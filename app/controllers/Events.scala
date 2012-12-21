package controllers

import play.api.mvc.Controller
import play.api.libs.json.Json._
import play.api.libs.json.JsValue
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.{TimeUnit, LinkedBlockingDeque}

object Events extends Controller with Secured {

  val counter = new AtomicInteger()

  val queue = new LinkedBlockingDeque[HtmlFragmentContainer]()


  def events = IsAuthenticated {
    _ => implicit request => {
      val fragmentContainer = Option(queue.pollFirst(5, TimeUnit.SECONDS)).getOrElse(HtmlFragmentContainer())

      Ok(fragmentContainer.asJson)
    }
  }

}