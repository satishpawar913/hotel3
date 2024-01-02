package com.springboot.demo.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.springboot.demo.entities.Rooms;
import com.springboot.demo.entities.User;
import com.springboot.demo.helper.Message;
import com.springboot.demo.repository.RoomRepository;
import com.springboot.demo.repository.UserRepository;
import com.springboot.demo.service.ManagerServcie;
import com.springboot.demo.service.RoomService;
import com.springboot.demo.service.UserService;

@Controller
public class TestController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ManagerServcie managerServcie;

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoomRepository roomRepository;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String start() {
		return "login";
	}

	@RequestMapping(value = { "/signup" })
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = { "/hotel" }, method = RequestMethod.GET)
	public String hotel() {
		return "hotel";
	}

	@RequestMapping(value = { "/room_booking" })
	public String room_booking(Model model) {
		model.addAttribute("rooms", new Rooms());
		return "room_booking";
	}

	@RequestMapping(value = { "/facilities" })
	public String facilities() {
		return "facilities";
	}

	@RequestMapping(value = { "/restaurant" })
	public String restaurant() {
		return "restaurant";
	}

	@RequestMapping(value = { "/user/data" })
	public String userdashboard() {
		return "userdashboard";
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
	public RedirectView registerUser(@ModelAttribute("user") User user, Model model, HttpSession session,
			RedirectAttributes redir) {

		user.setRole("ROLE_CUSTOMER");
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println("USER " + user);

		User result = this.userRepository.save(user);

		RedirectView redirectView = new RedirectView("/login", true);
		redir.addFlashAttribute("message", "You successfully registered! You can now login");
		return redirectView;

	}

	@RequestMapping(value = { "/userdashboard" })
	public String userDashboardInfo(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		// Assuming the userRepository is autowired in your controller
		String name = principal.getName();
		User user = this.userRepository.getUserByUserName(name);

		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("user", user);
		System.out.println("User " + user);

		return "userdashboard";
	}
}
