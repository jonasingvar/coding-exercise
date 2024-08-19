package io.navalia.jonasingvar.web.order;

import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {
    private List<ProductDTO> products;
}