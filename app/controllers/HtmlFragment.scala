package controllers

import play.api.libs.json.JsValue
import play.api.templates.Html
import play.api.libs.json.Json._

case class HtmlFragment(id: String)(f: String => Html) {

  lazy val asJson: JsValue = toJson(Map(
    "id" -> toJson(id),
    "html" -> toJson(f(id).body)
  ))
}

case class HtmlFragmentContainer(fragments: HtmlFragment*) {
  lazy val asJson: JsValue = toJson(Map(
    "fragments" -> fragments.map(_.asJson)
  ))
}