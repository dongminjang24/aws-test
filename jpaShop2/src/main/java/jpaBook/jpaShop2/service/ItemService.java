package jpaBook.jpaShop2.service;

import jakarta.persistence.EntityManager;
import jpaBook.jpaShop2.domain.item.Book;
import jpaBook.jpaShop2.domain.item.Item;
import jpaBook.jpaShop2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional// 변경 감지에 의한 업데이트
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {

        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
        findItem.setPrice(price);
    }
//    @Transactional// 변경 감지에 의한 업데이트
//    public Item updateItem(Long itemId, Book book) {
//        Item findItem = itemRepository.findOne(itemId);
//        findItem.setName(book.getName());
//        findItem.setStockQuantity(book.getStockQuantity());
//        findItem.setPrice(book.getPrice());
//    return findItem;//    }

    public List<Item> findItem() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
