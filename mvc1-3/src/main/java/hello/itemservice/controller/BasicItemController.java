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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	
	@GetMapping("/{itemId}/edit")
	public String editForm(Model model, @PathVariable long itemId) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute(item);
		return "basic/editForm";
	}

	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable long itemId, @ModelAttribute ItemParamDto item, Model model) {
		itemRepository.update(itemId, item);
		return "redirect:/basic/items/{itemId}";	// @PathVariable 값 사용 가능
	}
	
	@GetMapping("/add")
	public String addForm() {
		return "basic/addForm";
	}
	
//	@PostMapping("/add")
	public String saveV1(@ModelAttribute Item item, Model model) {
		itemRepository.save(item);
		model.addAttribute(item);
		return "basic/item";
	}
	
//	@PostMapping("/add")
	public String saveV2(@ModelAttribute Item item) {
		itemRepository.save(item);
		return "basic/item";
	}
	
	@PostMapping("/add")
	public String saveV3(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/basic/items/{itemId}";
	}
	
	// test용 데이터
	@PostConstruct	// 의존성 주입 후 초기화 수행하는 메소드
	public void init() {
		itemRepository.save(new Item("itemA",50000,3));
		itemRepository.save(new Item("itemB",10000,0));
	}
}
