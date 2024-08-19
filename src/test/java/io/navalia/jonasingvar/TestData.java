package io.navalia.jonasingvar;

import io.navalia.jonasingvar.application.dto.OrderDTO;
import io.navalia.jonasingvar.application.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public class TestData {

  public static OrderDTO getOrderDTO() {
    var orderDTO = new OrderDTO();
    orderDTO.setProducts(List.of(new ProductDTO("HAT-12", 2, new BigDecimal(39), new BigDecimal(5), "Hat"),
        new ProductDTO("JACK-13", 1, new BigDecimal(149), new BigDecimal(10), "Jacket")));

    return orderDTO;
  }
}
