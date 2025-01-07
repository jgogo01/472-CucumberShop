package ku.shop;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import javax.naming.InsufficientResourcesException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyStepdefs {

    private ProductCatalog catalog;
    private Order order;
    private Product product;
    private Exception thrownException;

    @Given("the store is ready to service customers")
    public void the_store_is_ready_to_service_customers() {
        catalog = new ProductCatalog();
        order = new Order();
    }

    @Given("a product {string} with price {float} and stock of {int} exists")
    public void a_product_exists(String name, double price, int stock) {
        catalog.addProduct(name, price, stock);
    }

    @When("I buy {string} with quantity {int}")
    public void i_buy_with_quantity(String name, int quantity){
        try{
            Product prod = catalog.getProduct(name);
            order.addItem(prod, quantity);
        } catch (InsufficientResourcesException e){
            thrownException = e;
        }
    }

    @Then("throw Quantity exceeds stock")
    public void throw_Quantity_exceeds_stock() {
        assertEquals("Quantity exceeds stock", this.thrownException.getMessage());
    }

    @Then("total should be {float}")
    public void total_should_be(double total) {
        assertEquals(total, order.getTotal());
    }

    @When("I want to check {string} stock")
    public void when_I_want_to_check_stock(String product) {
        this.product = catalog.getProduct(product);
    }

    @Then("stock should be {int}")
    public void then_stock_should_be(int stock) {
        assertEquals(product.getStock(), stock);
    }
}

