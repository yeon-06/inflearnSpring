package hello.itemservice.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
	private final ItemRepository itemRepository;
	
	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "basic/items";
	}
	
	// test용 데이터
	@PostConstruct	// 의존성 주입 후 초기화 수행하는 메소드
	public void init() {
		itemRepository.save(new Item("itemA",50000,3));
		itemRepository.save(new Item("itemB",10000,0));
	}
}
