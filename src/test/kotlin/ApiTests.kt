import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ApiTests {
    private val client = HttpClient(CIO)

    private suspend fun log(response: HttpResponse) {
        println("Status: ${response.status}")
        println("Info: ${response.bodyAsText()}")
    }

    @AfterAll
    fun closeClient() {
        client.close()
    }

    @Test
    @Order(1)
    fun testPostPet() = runBlocking {
        val body = """
            {
              "id": 9876,
              "category": { "id": 1, "name": "cats" },
              "name": "Vasiliy",
              "photoUrls": [ "https://i.ytimg.com/vi/Ptq-r7Gdwt4/maxresdefault.jpg" ],
              "tags": [ { "id": 1, "name": "wonderful" } ],
              "status": "available"
            }
        """.trimIndent()
        val response = client.post("https://petstore.swagger.io/v2/pet") {
            header("Content-Type", "application/json")
            setBody(body)
        }
        assertEquals(200, response.status.value)
        log(response)
    }

    @Test
    @Order(2)
    fun testGetPetById() = runBlocking {
        val response: HttpResponse = client.get("https://petstore.swagger.io/v2/pet/9876")
        assertEquals(200, response.status.value)
        val bodyText = response.bodyAsText()
        assertTrue(bodyText.contains("\"id\":9876"))
        log(response)
    }

    @Test
    @Order(3)
    fun testFindPetsByStatus() = runBlocking {
        val response: HttpResponse = client.get("https://petstore.swagger.io/v2/pet/findByStatus?status=pending&status=sold")
        assertEquals(200, response.status.value)
        log(response)
    }

    @Test
    @Order(4)
    fun testPutPet() = runBlocking {
        val body = """
            {
              "id": 9876,
              "category": { "id": 1, "name": "cats" },
              "name": "Vasiliy",
              "photoUrls": [ "https://i.ytimg.com/vi/Ptq-r7Gdwt4/maxresdefault.jpg" ],
              "tags": [ { "id": 1, "name": "wonderful" } ],
              "status": "pending"
            }
        """.trimIndent()
        val response: HttpResponse = client.put("https://petstore.swagger.io/v2/pet") {
            header("Content-Type", "application/json")
            setBody(body)
        }
        assertEquals(200, response.status.value)
        log(response)
    }

    @Test
    @Order(5)
    fun testDeletePetById() = runBlocking {
        val response: HttpResponse = client.delete("https://petstore.swagger.io/v2/pet/9876") {
            header("api_key", "special-key")
        }
        assertEquals(200, response.status.value)
        println("Status: ${response.status}")
    }

    @Test
    @Order(6)
    fun testDeleteFakePetById() = runBlocking {
        val response: HttpResponse = client.delete("https://petstore.swagger.io/v2/pet/-9999999") {
            header("api_key", "special-key")
        }
        assertEquals(404, response.status.value)
        println("Status: ${response.status}")
    }

    @Test
    @Order(7)
    fun testGetStoreInventory() = runBlocking {
        val response: HttpResponse = client.get("https://petstore.swagger.io/v2/store/inventory")
        assertEquals(200, response.status.value)
        log(response)
    }

    @Test
    @Order(8)
    fun testPostOrder() = runBlocking {
        val body = """
            {
              "id": 7,
              "petId": 9786,
              "quantity": 1,
              "shipDate": "2025-12-03T20:44:58.616Z",
              "status": "placed",
              "complete": true
            }
        """.trimIndent()
        val response: HttpResponse = client.post("https://petstore.swagger.io/v2/store/order") {
            header("Content-Type", "application/json")
            setBody(body)
        }
        assertEquals(200, response.status.value)
        log(response)
    }

    @Test
    @Order(9)
    fun testGetOrderById() = runBlocking {
        val response: HttpResponse = client.get("https://petstore.swagger.io/v2/store/order/7")
        assertEquals(200, response.status.value)
        val bodyText = response.bodyAsText()
        assertTrue(bodyText.contains("\"id\":7"))
        assertTrue(bodyText.contains("\"status\":\"placed\""))
        log(response)
    }

    @Test
    @Order(10)
    fun testDeleteOrderById() = runBlocking {
        val response: HttpResponse = client.delete("https://petstore.swagger.io/v2/store/order/7")
        assertEquals(200, response.status.value)
        println("Status: ${response.status}")
    }

    @Test
    @Order(11)
    fun testUser() = runBlocking {
        var response: HttpResponse = client.get("https://petstore.swagger.io/v2/user/login?username=tempuser&password=1234")
        assertEquals(200, response.status.value)
        log(response)
        var body = """
            {
              "id": 45,
              "username": "temp_user",
              "firstName": "abc",
              "lastName": "def",
              "email": "test@ya.ru",
              "password": "1234",
              "phone": "+79012345678",
              "userStatus": 1
            }
        """.trimIndent()
        response = client.post("https://petstore.swagger.io/v2/user") {
            header("Content-Type", "application/json")
            setBody(body)
        }
        assertEquals(200, response.status.value)
        var bodyText = response.bodyAsText()
        assertTrue(bodyText.contains("\"message\":\"45\""))
        log(response)
        body = """
            {
              "id": 45,
              "username": "new_user",
              "firstName": "abc",
              "lastName": "def",
              "email": "test@ya.ru",
              "password": "1234",
              "phone": "+79012345678",
              "userStatus": 1
            }
        """.trimIndent()
        response = client.put("https://petstore.swagger.io/v2/user/temp_user") {
            header("Content-Type", "application/json")
            setBody(body)
        }
        assertEquals(200, response.status.value)
        bodyText = response.bodyAsText()
        assertTrue(bodyText.contains("\"message\":\"45\""))
        log(response)
    }
}