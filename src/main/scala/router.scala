import akka.http.scaladsl.server.{Directives, Route}

trait Router {
  def route: Route
}

class TodoRouter(todoRepository: TodoRepository) extends Router with Directives with TodoDirectives with ValidatorDirectives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  override def route: Route = pathPrefix("todos") {
    pathEndOrSingleSlash {
      get {
        handleWithGeneric(todoRepository.all())(complete(_))
      } ~ post {
        entity(as[CreateTodo]) { createTodo =>
          validateWith(CreateTodoValidator)(createTodo){
            handleWithGeneric(todoRepository.save(createTodo))(complete(_))
          }
        }
      }
    } ~ path(Segment) { id: String =>
      put {
        entity(as[UpdateTodo]) { updateTodo =>
          validateWith(UpdateTodoValidator)(updateTodo) {
            handle(todoRepository.update(id, updateTodo)) {
              case TodoRepository.TodoNotFound(_) => ApiError.todoNotFound(id)
              case _ => ApiError.generic
            }{ todo =>
              complete(todo)
            }
          }
        }
      }
    } ~ path("done") {
      get {
        handleWithGeneric(todoRepository.done())(complete(_))
      }
    } ~ path("pending") {
      get {
        handleWithGeneric(todoRepository.pending())(complete(_))
      }
    }
  }
}
