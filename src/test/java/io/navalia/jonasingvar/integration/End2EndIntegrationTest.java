package io.navalia.jonasingvar.integration;

import io.navalia.jonasingvar.TestData;
import io.navalia.jonasingvar.application.dto.OrderDTO;
import io.navalia.jonasingvar.application.dto.OrderResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/*
 This class does a full end to end integration test with H2
 It first creates an order through the API that gets saved to the database
 It then uses the ID in the response to call the GET API to retrieve it and compares that it was saved correctly
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class End2EndIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private String baseUrl;

  @BeforeEach
  void setUp() {
    baseUrl = "http://localhost:" + port + "/api/orders";
  }

  @Test
  void submits_order_and_finds_by_get_request() {
    var response = submitOrder();
    verifyOrder(response.getId());
  }

  private OrderResponseDTO submitOrder() {
    var headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    var request = new HttpEntity<OrderDTO>(TestData.getOrderDTO(), headers);
    var response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, OrderResponseDTO.class);
    OrderResponseDTO responseDTO = response.getBody();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(responseDTO).isNotNull();
    assertThat(responseDTO.getId()).isNotNull();
    return responseDTO;
  }

  private void verifyOrder(UUID id) {
    var getOrderResponse = restTemplate.getForEntity(baseUrl + "/" + id, OrderResponseDTO.class);
    assertThat(getOrderResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    var body = getOrderResponse.getBody();
    assertThat(body).isNotNull();
    assertThat(body.getId()).isEqualTo(id);
    assertThat(body.getProducts().size()).isEqualTo(2);

    var product = body.getProducts().get(0);
    var expectedProduct = TestData.getOrderDTO().getProducts().get(0);

    assertThat(product)
        .usingRecursiveComparison()
        .withComparatorForType(BigDecimal::compareTo, BigDecimal.class) // Compares BigDecimal numerically
        .isEqualTo(expectedProduct);
  }
}