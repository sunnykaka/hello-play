package controllers

import play.api._
import play.api.mvc._
import scala.concurrent.Future

object Application extends Controller {

  //FIXME 1. 为什么welcome里的方法在Action里能访问到?
  //FIXME 2. 为什么request上要加implicit?

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def welcome(name: String) = Action {
    Ok("welcome redirect here:" + name)
  }

  def redirect_to(name: String) = Action {
    Redirect(routes.Application.welcome(name))
  }

  def hello(id: Option[Long]) = Action {implicit request =>
    Ok("hello world " + id.getOrElse(0))
  }

  object LoggingAction extends ActionBuilder[Request] {
    override protected def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[SimpleResult]): Future[SimpleResult] = {
      print("logger info")
      block(request)
    }
  }

  def loggerInfo(name: String) = LoggingAction (
    Ok("this is in logging action" + name)
  )

}