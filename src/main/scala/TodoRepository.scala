import TodoRepository.TodoNotFound

import scala.concurrent.{ExecutionContext, Future}

trait TodoRepository {
  def all(): Future[Seq[Todo]]
  def done(): Future[Seq[Todo]]
  def pending(): Future[Seq[Todo]]
  def save(createTodo: CreateTodo): Future[Todo]
  def update(id: String, updateTodo: UpdateTodo): Future[Todo]
}

object TodoRepository {
  final case class TodoNotFound(id: String) extends Exception(s"Todo with id $id not found.")
}

class InMemoryTodoRepository(initialTodos: Seq[Todo] = Seq(
  Todo("1", "Buy eggs", "Ran out of eggs, buy a dozen", done = false),
  Todo("2", "Buy milk", "The cat is thirsty", done = true)
))(implicit ec: ExecutionContext) extends TodoRepository {

  private var todos: Vector[Todo] = initialTodos.toVector

  override def all(): Future[Seq[Todo]] = Future.successful(todos)

  override def done(): Future[Seq[Todo]] = Future.successful(todos.filter(_.done))

  override def pending(): Future[Seq[Todo]] = Future.successful(todos.filterNot(_.done))

  override def save(createTodo: CreateTodo): Future[Todo] = Future.successful {
    val todo = Todo(createTodo)
    todos = todos :+ todo
    todo
  }

  override def update(id: String, updateTodo: UpdateTodo): Future[Todo] = {
    todos.find(_.id == id) match {
      case Some(foundTodo) =>
        val newTodo = updateHelper(foundTodo, updateTodo)
        todos = todos.map(t => if (t.id == id) newTodo else t)
        Future.successful(newTodo)
      case None => Future.failed(TodoNotFound(id))
    }
  }

  private def updateHelper(todo: Todo, updateTodo: UpdateTodo): Todo = {
    val nTitle = updateTodo.title.getOrElse(todo.title)
    val nDescription = updateTodo.description.getOrElse(todo.description)
    val nDone = updateTodo.done.getOrElse(todo.done)
    todo.copy(title = nTitle, description = nDescription, done = nDone)
  }
}
