package hello.itemservice.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemParamDto;

@Repository
public class ItemRepository {
	private static final Map<Long, Item> store = new ConcurrentHashMap<>();
	private static long sequence = 0L;
	
	public Item save(Item item) {
		item.setId(++sequence);
		store.put(item.getId(), item);
		return item;
	}
	
	public void update(Long itemId, ItemParamDto updateParam) {
		Item item = findById(itemId);
		item.setName(updateParam.getName());
		item.setPrice(updateParam.getPrice());
		item.setQuantity(updateParam.getQuantity());
	}
	
	public List<Item> findAll() {
		return new ArrayList<>(store.values());
	}
	
	public Item findById(Long sequence) {
		return store.get(sequence);
	}
	
	public void clearStore() {
		store.clear();
	}
}
