import java.util.UUID

case class Todo(id: String, title: String, description: String, done: Boolean)

object Todo {
  def apply(createTodo: CreateTodo): Todo = new Todo(UUID.randomUUID().toString, createTodo.title, createTodo.description, false)
}

case class CreateTodo(title: String, description: String)
case class UpdateTodo(title: Option[String], description: Option[String], done: Option[Boolean])
