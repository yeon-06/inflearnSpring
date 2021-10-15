package hello.itemservice.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemParamDto;
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
	
	@GetMapping("/{itemId}")
	public String item(Model model, @PathVariable long itemId) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/item";
	}
	
	
	@GetMapping("/add")
	public String addForm() {
		return "basic/addForm";
	}
	
	@PostMapping("/add/v1")
	public String saveV1(@ModelAttribute Item item, Model model) {
		itemRepository.save(item);
		model.addAttribute(item);
		return "basic/item";
	}
	
	@PostMapping("/add")
	public String saveV2(@ModelAttribute("item") Item item) {
		itemRepository.save(item);
		return "basic/item";
	}
	
	// test용 데이터
	@PostConstruct	// 의존성 주입 후 초기화 수행하는 메소드
	public void init() {
		itemRepository.save(new Item("itemA",50000,3));
		itemRepository.save(new Item("itemB",10000,0));
	}
}
