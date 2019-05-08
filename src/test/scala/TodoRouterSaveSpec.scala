import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.gitub.duncannevin.secretmessenger.{CreateTodo, InMemoryTodoRepository, Todo, router}
import org.scalatest.{Matchers, WordSpec}

class TodoRouterSaveSpec extends WordSpec with Matchers with ScalatestRouteTest with TodoMocks {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  val testSaveTodo = CreateTodo(
    "test todo",
    "test description"
  )

  "A com.gitub.duncannevin.secretmessenger.router" should {
    "create a todo with valid data" in {
      val repository = new InMemoryTodoRepository()
      val router = new router(repository)

      Post("/todos", testSaveTodo) ~> router.route ~> check {
        status shouldBe StatusCodes.OK
        val resp = responseAs[Todo]
        resp.title shouldBe testSaveTodo.title
        resp.description shouldBe testSaveTodo.description
      }
    }
    "not create a todo with invalid data" in {
      val repository = new FailingRepository
      val router = new router(repository)

      Post("/todos", testSaveTodo.copy("")) ~> router.route ~> check {
        status shouldBe ApiError.emptyTitleField.statusCode
        val resp = responseAs[String]
        resp shouldBe ApiError.emptyTitleField.message
      }
    }
  }
}
