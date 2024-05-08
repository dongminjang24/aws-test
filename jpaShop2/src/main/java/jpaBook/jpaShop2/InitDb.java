package jpaBook.jpaShop2;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpaBook.jpaShop2.domain.*;
import jpaBook.jpaShop2.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();


    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager entityManager;
        public void dbInit1() {
            Member member = createMember("UserA","서울","1","1111");
            entityManager.persist(member);

            Book book = getBook("JPA1 BOOK",1000,108);
            entityManager.persist(book);


            Book book2 = getBook("JPA2 BOOK",2000,180);
            entityManager.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            entityManager.persist(order);
        }
        public void dbInit2() {
            Member member = createMember("UserB","진주","2","2222");
            entityManager.persist(member);

            Book book = getBook("Spring1 BOOK",1000,100);
            entityManager.persist(book);


            Book book2 = getBook("Spring2 BOOK",3000,200);
            entityManager.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 20000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 2);

            Delivery delivery = getDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            entityManager.persist(order);
        }

        private static Delivery getDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static Book getBook(String name,int price,int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }


        private static Member createMember(String user,String city,String street,String zipCode) {
            Member member = new Member();
            member.setName(user);
            member.setAddress(new Address(city, street, zipCode));
            return member;
        }
    }
}
