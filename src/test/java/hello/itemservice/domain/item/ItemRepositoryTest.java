package hello.itemservice.domain.item;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import hello.itemservice.domain.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemRepositoryTest {
	private ItemRepository itemRepo = new ItemRepository();
	
	@AfterEach
	void afterEach() {
		itemRepo.clearStore();
	}
	
	@Test
	void 등록_테스트() {
		// given
		Item item = new Item("마우스",50000,3);
		
		// when
		Item savedItem = itemRepo.save(item);
		
		// then
		Item findItem = itemRepo.findById(item.getId());
		assertThat(findItem).isEqualTo(savedItem);
	}
	
	@Test
	void 조회_테스트() {
		Item itemA = new Item("itemA",50000,3);
		Item itemB = new Item("itemB",10000,0);
		
		itemRepo.save(itemA);
		itemRepo.save(itemB);

		List<Item> itemList = itemRepo.findAll();
		assertThat(itemList.size()).isEqualTo(2);
		assertThat(itemList).contains(itemA, itemB);
	}
	
	@Test
	void 수정_테스트() {
		Item item = new Item("마우스", 50000, 3);
		Item savedItem = itemRepo.save(item);
		Long id = savedItem.getId();
		
		ItemParamDto updateItem = new ItemParamDto("마우스", 2000, 3);
		itemRepo.update(id, updateItem);
		
		Item findItem = itemRepo.findById(id);
		
		assertThat(updateItem.getName()).isEqualTo(findItem.getName());
		assertThat(updateItem.getPrice()).isEqualTo(findItem.getPrice());
		assertThat(updateItem.getQuantity()).isEqualTo(findItem.getQuantity());
	}
	
}
