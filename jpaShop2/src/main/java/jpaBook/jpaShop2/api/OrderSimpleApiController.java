package jpaBook.jpaShop2.api;

import jpaBook.jpaShop2.domain.Address;
import jpaBook.jpaShop2.domain.Order;
import jpaBook.jpaShop2.domain.OrderSimpleQueryDto;
import jpaBook.jpaShop2.domain.OrderStatus;
import jpaBook.jpaShop2.repository.OrderRepository;
import jpaBook.jpaShop2.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/*
 * Order
 * Order -> Member
 * Order -> Delivery
 * */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/sample-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Lazy강제 초기화
            order.getDelivery().getAddress(); // Lazy강제 초기화
        }
        return all;
    }
    @GetMapping("/api/v2/sample-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<SimpleOrderDto> result = orderRepository.findAll(
                new OrderSearch()).stream().map(SimpleOrderDto::new).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> collect = orders.stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderRepository.findOrderDtos();
    }



    @Data
    @RequiredArgsConstructor
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getMember().getAddress();
        }
    }
}
