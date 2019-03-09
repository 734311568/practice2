package com.bigbigmall.xiamen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@Controller
@RequestMapping("/")
public class WelcomeController {

	@RequestMapping("/")
	void index() {
	}
}
