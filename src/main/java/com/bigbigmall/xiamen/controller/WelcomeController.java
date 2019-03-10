package com.bigbigmall.xiamen.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author P-C Lin (a.k.a 高科技黑手)
 */
@Controller
@RequestMapping("/")
public class WelcomeController {

	@RequestMapping("/")
	@ResponseBody
	void index(HttpServletResponse response) throws ParserConfigurationException, TransformerConfigurationException, IOException, TransformerException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element documentElement = document.createElement("document");
		document.appendChild(documentElement);

		TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(response.getOutputStream()));
	}
}
