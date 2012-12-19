package views.helper

import views.html.helper.{FieldElements, FieldConstructor}
import views.html.helper.bootstrap2.bootstrap2FieldConstructor

package object bootstrap2 {
  implicit val bootstrap2Field = new FieldConstructor {
     def apply(elts: FieldElements) = bootstrap2FieldConstructor(elts)
   }
}
