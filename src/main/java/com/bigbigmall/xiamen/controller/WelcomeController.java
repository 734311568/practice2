package com.bigbigmall.xiamen.controller;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * @auther 7343
 */
@Controller
@RequestMapping("/")
public class WelcomeController {

	/**
	 * 4月3号
	 *
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/Personalpage")
	@ResponseBody
	public ModelAndView getPersonalpage(HttpServletResponse response, HttpServletRequest request) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element documentElement = doc.createElement("document");
		doc.appendChild(documentElement);
		CloseableHttpResponse response1 = HttpClients.createDefault().execute(new HttpGet("https://redan-api.herokuapp.com/personnels/search/findOneById?id=3"));
		HttpEntity entity = response1.getEntity();
		if (null != entity) {
			String getDataStr = EntityUtils.toString(entity, "UTF-8");
			JSONObject jsonObjectDataStr = new JSONObject(getDataStr);

			//创建nickname
			Element nicknameElement = doc.createElement("nickname");
			String nicknameString = jsonObjectDataStr.get("nickname").toString();
			nicknameElement.appendChild(doc.createTextNode(nicknameString));
			documentElement.appendChild(nicknameElement);

			//创建followingCount
			Element followingCountElement = doc.createElement("followingCount");
			String followingCountString = jsonObjectDataStr.get("followingCount").toString();
			followingCountElement.appendChild(doc.createTextNode(followingCountString));
			documentElement.appendChild(followingCountElement);

			//创建followerCount
			Element followerCountElement = doc.createElement("followerCount");
			String followerCountString = jsonObjectDataStr.get("followerCount").toString();
			followerCountElement.appendChild(doc.createTextNode(followerCountString));
			documentElement.appendChild(followerCountElement);

			//创建userStoryCount
			Element userStoryCountElement = doc.createElement("userStoryCount");
			String userStoryCountString = jsonObjectDataStr.get("userStoryCount").toString();
			userStoryCountElement.appendChild(doc.createTextNode(userStoryCountString));
			documentElement.appendChild(userStoryCountElement);

			//创建coverImgUrl
			Element coverImgUrlElement = doc.createElement("coverImgUrl");
			String coverImgUrlString = jsonObjectDataStr.get("coverImgUrl").toString();
			coverImgUrlElement.setAttribute("cover", coverImgUrlString);
			documentElement.appendChild(coverImgUrlElement);

			//创建profileImgUrl
			Element profileImgUrlElement = doc.createElement("profileImgUrl");
			String profileImgUrlString = jsonObjectDataStr.get("profileImgUrl").toString();
			profileImgUrlElement.setAttribute("cover", profileImgUrlString);
			documentElement.appendChild(profileImgUrlElement);

			//创建profileText
			Element profileTextUrlElement = doc.createElement("profileText");
			String profileTextString = jsonObjectDataStr.get("profileText").toString();
			profileTextUrlElement.appendChild(doc.createTextNode(profileTextString));
			documentElement.appendChild(profileTextUrlElement);

			//创建cuserStory  第一层
			JSONArray jsonArrayUserStorys = jsonObjectDataStr.getJSONArray("userStory");

			Element userStoryElement = doc.createElement("userStorys");

			for (int i = 0; i < jsonArrayUserStorys.length(); i++) {
				Element userElement = doc.createElement("userStory");
				JSONObject jsonObjectUserStory = jsonArrayUserStorys.getJSONObject(i);
				System.out.println("jsonObjectUserStory\t" + jsonObjectUserStory);

				//创建storyImage  第二层
				JSONObject jsonObjectStoryImage = jsonObjectUserStory.getJSONObject("storyImage");
				Element storyImageElement = doc.createElement("storyImage");
				userElement.appendChild(storyImageElement);

				//创建 imgUrl 第三层
				String imgUrlString = jsonObjectStoryImage.get("imgUrl").toString();
				Element imgUrlElement = doc.createElement("imgUrl");
				imgUrlElement.setAttribute("att", imgUrlString);
				storyImageElement.appendChild(imgUrlElement);

				userElement.appendChild(storyImageElement);
				userStoryElement.appendChild(userElement);
			}
			documentElement.appendChild(userStoryElement);

		}
		Source source = new DOMSource(doc);
		ModelAndView model = new ModelAndView("Personalpage");
		model.addObject("xmlSource", source);
		return model;
//
//		TransformerFactory newTransformer = TransformerFactory.newInstance();
//		Transformer transformer = newTransformer.newTransformer();
//		transformer.transform(new DOMSource(doc), new StreamResult(response.getOutputStream()));
	}

	/**
	 * 三级联动
	 *
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sanJiLianDong")
	//@RequestBody
	@ResponseBody
	public String getsanJiLianDong(HttpServletResponse response, @RequestParam(value = "id", required = false) String id) throws Exception {

		CloseableHttpResponse response1 = HttpClients.createDefault().execute(new HttpGet("http://192.168.101.8:31001/config/arr?nid=" + id));

		HttpEntity entity = response1.getEntity();
		String srtFirst = null;
		if (null != entity) {
			srtFirst = EntityUtils.toString(entity, "UTF-8");
			System.out.println(srtFirst);
		}
		return srtFirst;
	}

	/**
	 * 获取nid 的键
	 *
	 * @param jsarr
	 * @return
	 */
	public String[] getNidKeys(JSONArray jsarr) {
		String[] nid = new String[3];
		for (int i = 0; i < jsarr.length(); i++) {
			JSONObject jsonObject = jsarr.getJSONObject(i);
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String nextKey = keys.next();
				if (nextKey.equals("nid")) {
					nid[i] = nextKey;
				}

			}
		}

		return nid;

	}

	/**
	 * 拿到这key 键对应的value
	 *
	 * @param jsarr
	 * @return
	 */
	public String getkey(JSONArray jsarr) {
		String values = null;
		String[] nidKeys = getNidKeys(jsarr);

		for (int i = 0; i < jsarr.length(); i++) {
			JSONObject jsonObject = jsarr.getJSONObject(i);
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String next = keys.next();
				if (nidKeys[i].equals(next)) {
					//拿到nid 的值
					String nidValue = jsonObject.get(nidKeys[i]).toString();
					values = nidValue;
				}

			}
		}
		return values;

	}

	/**
	 * js创建文本
	 *
	 * @param response
	 * @throws ParserConfigurationException
	 * @throws TransformerConfigurationException
	 * @throws IOException
	 * @throws TransformerException
	 */
	@RequestMapping("/")
	@ResponseBody
	void index(HttpServletResponse response) throws ParserConfigurationException, TransformerConfigurationException, IOException, TransformerException {
		//创建文档
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		//根元素
		Element documentElement = document.createElement("document");
		documentElement.setAttribute("user", "me");
		Element stories = document.createElement("stories");
		stories.setAttribute("status", "200");
		//第一个故事
		Element story1 = document.createElement("story");
		Element comments = document.createElement("comments");
		Element comment = document.createElement("comment");
		Element id = document.createElement("id");

		id.appendChild(document.createTextNode("defad05a-0eb1-4545-b4aa-39460f174fbc"));
		Element content = document.createElement("content");
		//content.setTextContent();
		content.appendChild(document.createTextNode("這是一篇好文章阿"));
		Element who = document.createElement("who");
		Element nickname = document.createElement("nickname");
		Element pcid = document.createElement("id");
		Text createTextNode = document.createTextNode("7f84cc84-bb50-4cdf-8578-ef2827d35726");
		pcid.appendChild(createTextNode);
		nickname.appendChild(document.createTextNode("pc"));

		who.appendChild(nickname);
		who.appendChild(pcid);
		comment.appendChild(id);
		comment.appendChild(content);
		comment.appendChild(who);

		//第一个故事的comment2
		Element comment2 = document.createElement("comment");
		Element id2 = document.createElement("id");
		id2.appendChild(document.createTextNode("8ee5d256-a4e9-4c67-a5d0-0699d9b0cbd7"));

		Element content2 = document.createElement("content");

		content2.appendChild(document.createTextNode("家惠什麼時候要開始減肥呀"));
		Element who2 = document.createElement("who");
		Element nickname2 = document.createElement("nickname");

		nickname2.appendChild(document.createTextNode("pc"));
		Element id3 = document.createElement("id");

		id3.appendChild(document.createTextNode("7f84cc84-bb50-4cdf-8578-ef2827d35726"));
		who2.appendChild(nickname2);
		who2.appendChild(id3);
		comment2.appendChild(who2);
		comment2.appendChild(content2);

		Element emotions = document.createElement("emotions");
		emotions.setTextContent("0");
		Element postedAt = document.createElement("postedAt");

		postedAt.appendChild(document.createTextNode("2014-02-11T00:00:00+08:00"));
		Element author = document.createElement("author");
		Element nickname1 = document.createElement("nickname");
		nickname1.setTextContent("redan");
		Element authorId = document.createElement("id");

		authorId.appendChild(document.createTextNode("e4b6a337-6647-4521-ac9a-c4e0a3853626"));
		author.appendChild(nickname1);
		author.appendChild(authorId);
		comments.appendChild(comment);
		comments.appendChild(comment2);
		story1.appendChild(comments);
		story1.appendChild(emotions);
		story1.appendChild(postedAt);
		story1.appendChild(author);
		Element story2 = document.createElement("story");
		Element idT = document.createElement("id");
		idT.appendChild(document.createTextNode("190cc757-7a0e-4712-97c9-bf5f77fd5a8e"));

		Element contentT = document.createElement("content");

		contentT.appendChild(document.createTextNode("this is content 這是一篇文章"));
		story1.appendChild(idT);
		story1.appendChild(contentT);

		Element story3 = document.createElement("story");
		//追加各层的子节点

		//第二个故事
		Element emotions2 = document.createElement("emotions");
		emotions2.setTextContent("0");
		Element postedAt2 = document.createElement("postedAt");

		postedAt2.appendChild(document.createTextNode("2019-01-30 03:08:30.556067"));
		Element author3 = document.createElement("author");
		Element hid = document.createElement("id");

		hid.appendChild(document.createTextNode("fdb53388-243b-11e9-b2d7-23d188ab349e"));
		Element nickname3 = document.createElement("nickname");
		nickname3.appendChild(document.createTextNode("阿惠"));

		author3.appendChild(hid);
		author3.appendChild(nickname3);
		story2.appendChild(emotions2);

		story2.appendChild(postedAt2);
		story2.appendChild(author3);
		Element idL = document.createElement("id");
		idL.appendChild(document.createTextNode("72ff2337-1b0b-473c-8120-6ff028632806"));

		Element contentL = document.createElement("content");
		contentL.appendChild(document.createTextNode("路，就是一條直直的。認同請按讚，不認同請分享"));

		story2.appendChild(idL);
		story2.appendChild(contentL);

		//第三故事
		Element emotions3 = document.createElement("emotions");

		emotions3.appendChild(document.createTextNode("0"));
		Element postedAt3 = document.createElement("postedAt");

		postedAt3.appendChild(document.createTextNode("2019-01-30 03:08:54.812136"));
		Element author4 = document.createElement("author");

		Element nickname4 = document.createElement("nickname");

		nickname4.appendChild(document.createTextNode("生仔"));
		Element id4 = document.createElement("id");

		id4.appendChild(document.createTextNode("f45f0aa2-243b-11e9-b2d7-7339b94259f1"));
		author4.appendChild(nickname4);
		author4.appendChild(id4);
		Element id5 = document.createElement("id");

		id5.appendChild(document.createTextNode("5bc82c97-14e7-44ad-91ea-8941a12eae43"));
		Element content5 = document.createElement("content");

		content5.appendChild(document.createTextNode("累、累、累！"));
		story3.appendChild(emotions3);
		story3.appendChild(postedAt3);
		story3.appendChild(author4);
		story3.appendChild(author4);

		story3.appendChild(id5);

		story3.appendChild(content5);

		stories.appendChild(story1);
		stories.appendChild(story2);

		stories.appendChild(story3);
		documentElement.appendChild(stories);
		document.appendChild(documentElement);
		//更新dom保持
		TransformerFactory newTransformer = TransformerFactory.newInstance();
		Transformer transformer = newTransformer.newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult(response.getOutputStream()));
	}

	@RequestMapping("/showXML")
	public void getView(HttpServletResponse response) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element documentElement = doc.createElement("document");
		doc.appendChild(documentElement);

		CloseableHttpResponse response1 = HttpClients.createDefault().execute(new HttpGet("https://redan-api.herokuapp.com/story/"));
		HttpEntity entity = response1.getEntity();
		if (null != entity) {
			String str = EntityUtils.toString(entity, "UTF-8");
			Element createStories = doc.createElement("stories");
			// story
			JSONArray jsonArrayResult = new JSONObject(str).getJSONArray("result");
			Element createResult = doc.createElement("story");
			JSONObject json = getJson(jsonArrayResult);

			//comments
			JSONArray jsonComments = json.getJSONArray("comments");
			Element comments = doc.createElement("comments");
			//comments的第一对象
			JSONObject commentsObject = jsonComments.getJSONObject(0);

			Element comment = doc.createElement("comment");
			String commentsGetId1 = commentsObject.get("id").toString();
			comment.setAttribute("id", commentsGetId1);
			Element Content1 = doc.createElement("content");
			String contentVaules = commentsObject.get("content").toString();
			Content1.appendChild(doc.createTextNode(contentVaules));

			comment.appendChild(Content1);
			Element who = doc.createElement("who");

			//who的对象
			JSONObject jsonObjectWho = commentsObject.getJSONObject("who");
			String whoId = jsonObjectWho.get("id").toString();
			String nicknameValue = jsonObjectWho.getString("nickname");
			who.setAttribute("id", whoId);
			who.setAttribute("nickname", nicknameValue);

			comment.appendChild(who);
			comments.appendChild(comment);

			//comments的第二个对象
			JSONObject commentsObject2 = jsonComments.getJSONObject(1);
			Element comment2 = doc.createElement("comment");
			String att = commentsObject2.getString("id");
			Element commentContent2 = doc.createElement("content");
			String content = commentsObject2.getString("content");
			commentContent2.appendChild(doc.createTextNode(content));
			comment2.setAttribute("id", att);
			comment2.appendChild(commentContent2);

			Element elementWho = doc.createElement("who");
			JSONObject who2 = commentsObject2.getJSONObject("who");
			String nickname2 = who2.getString("nickname");
			String stringId = who2.getString("id");
			elementWho.setAttribute("id", stringId);
			elementWho.setAttribute("nickname", nickname2);
			comment2.appendChild(elementWho);
			comments.appendChild(comment);
			comments.appendChild(comment2);

			//id
			String getId3 = json.get("id").toString();
			createResult.setAttribute("id", getId3);
			//emotions
			String emotionsValue = json.get("emotions").toString();
			createResult.setAttribute("emotions", emotionsValue);
			//postedAt
			String postedAtValue = json.get("postedAt").toString();
			createResult.setAttribute("postedAt", postedAtValue);
			//author
			Element createAuthor = doc.createElement("author");
			JSONObject authorObject = json.getJSONObject("author");
			String nicknameValue2 = authorObject.get("nickname").toString();
			String getId = authorObject.get("id").toString();
			createAuthor.setAttribute("id", getId);
			createAuthor.setAttribute("nickname", nicknameValue2);
			comments.appendChild(comment);
			comments.appendChild(comment2);

			String commentsId = json.get("id").toString();
			Element createElementContent = doc.createElement("content");
			String commentsContent = json.get("content").toString();
			createElementContent.appendChild(doc.createTextNode(commentsContent));
			comments.setAttribute("id", commentsId);
			comments.appendChild(createElementContent);
			createResult.appendChild(comments);
			createResult.appendChild(createAuthor);

			//status
			String statusValue = new JSONObject(str).get("status").toString();
			documentElement.setAttribute("status", statusValue);
			createStories.appendChild(createResult);
			documentElement.appendChild(createStories);

		}
		TransformerFactory newTransformer = TransformerFactory.newInstance();
		Transformer transformer = newTransformer.newTransformer();
		transformer.transform(new DOMSource(doc), new StreamResult(response.getOutputStream()));
	}

	/**
	 * 遍历json 数组返回json对象
	 *
	 * @param jSONArray
	 * @return
	 */
	public JSONObject getJson(JSONArray jSONArray) {
		JSONObject object = null;
		for (int i = 0; i < jSONArray.length(); i++) {
			object = jSONArray.getJSONObject(i);
		}

		return object;

	}

	/**
	 * 两个之间的所有的素数 3月20日
	 *
	 * @param minimum
	 * @param maximum
	 * @return
	 */
	@RequestMapping(value = "/json", method = RequestMethod.GET)
	@ResponseBody
	public String getAjax(@RequestParam(value = "minimum", required = false) Integer minimum, @RequestParam(value = "maximum") Integer maximum) {
		Integer num = null;
		StringBuffer sb = new StringBuffer();
		boolean falg;
		for (int i = minimum; i <= maximum; i++) {
			if (i == 1 || (i % 2 == 0 && i != 2)) {
				continue;
			}
			falg = true;
			for (int j = 2; j < i; j++) {
				if (i % j == 0) {
					falg = false;
					break;
				}
			}
			if (falg) {
				num = i;
				sb.append(num.toString());
				sb.append("\t");
			}
		}
		return sb.toString();
	}

	/**
	 * 处理九九乘法表 3月21号
	 *
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/for")
	@ResponseBody
	public String getFor(HttpServletResponse response) throws IOException {
		String kk = "k";
		String stu = "";
		for (int i = 2; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				System.out.print(j + "*" + i + "=" + i * j + "\t");
				if (i == 2 && j == 1) {
					if (j == 1 && (i == 2 || i == 3 || i == 5 || i == 7)) {
						stu += "[" + "{" + '"' + kk + '"' + ":" + '"' + i + "*" + j + "=" + "<span style='color:red'>" + i * j + "</span>" + '"' + "},";
					} else {
						stu += "[" + "{" + '"' + kk + '"' + ":" + '"' + i + "*" + j + "=" + i * j + '"' + "},";
					}
				} else if (i == 9 && j == 9) {

					stu += "{" + '"' + kk + '"' + ":" + '"' + i + "*" + j + "=" + i * j + '"' + "}" + "]";
				} else {
					if (j == 1 && (i == 2 || i == 3 || i == 5 || i == 7)) {
						stu += "{" + '"' + kk + '"' + ":" + '"' + i + "*" + j + "=" + "<span style='color:red'>" + i * j + "</span>" + '"' + "},";
					} else {
						stu += "{" + '"' + kk + '"' + ":" + '"' + i + "*" + j + "=" + i * j + '"' + "},";
					}
				}
			}
			System.out.println(" ");
		}
		return stu;
	}

	/**
	 * 把九九乘法表转换成XSLT 3月24号
	 *
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/showjson")
	public String getForJson(HttpServletResponse response, HttpServletRequest request) throws IOException {
		JSONArray jsonArray = new JSONArray();
		for (int i = 2; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("k1", i);
				jsonObject.put("k2", j);
				jsonObject.put("k3", i * j);
				jsonArray.put(jsonObject);
			}

		}
		request.getSession().setAttribute("stu", jsonArray.toString());
		return "forward:/XMLShow";
	}

	@RequestMapping(value = "/XMLShow")
	public ModelAndView getshowXML(HttpServletResponse response, HttpServletRequest request) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element documentElement = doc.createElement("document");
		doc.appendChild(documentElement);
		String attribute = (String) request.getSession().getAttribute("stu");
		JSONArray jsonArray = new JSONArray(attribute);
		createDom(jsonArray, doc, documentElement);
		Source source = new DOMSource(doc);
		ModelAndView model = new ModelAndView("multiplication");
		model.addObject("xmlSource", source);

		return model;

	}

	/**
	 * 获得json 的key
	 *
	 * @param objectJson
	 * @return
	 */
	public String[] getjsonValue(JSONObject objectJson) {
		String[] arrString = new String[3];
		Iterator<String> keys = objectJson.keys();
		for (int i = 0; i < objectJson.length(); i++) {
			String next = keys.next();
			arrString[i] = next;
		}
		return arrString;

	}

	/**
	 * 返回
	 *
	 * @param objectJson json数组
	 * @param i 元素下标
	 * @param pace 步距
	 * @param doc 文档
	 * @return
	 */
	public Element getElement(JSONArray objectJson, int i, int pace, Document doc) {
		Element keElement = doc.createElement("key");
		String[] jsonValue = getjsonValue(objectJson.getJSONObject(i + pace));
		String[] keyValuString = getKeyValuString(objectJson.getJSONObject(i + pace));

		for (int j = 0; j < jsonValue.length; j++) {

			if ((jsonValue[j].equals("k3") && keyValuString[j].equals("2")) || (jsonValue[j].equals("k3") && keyValuString[j].equals("3")) || (jsonValue[j].equals("k3") && keyValuString[j].equals("5")) || (jsonValue[j].equals("k3") && keyValuString[j].equals("7"))) {
				Element createElement1 = doc.createElement(jsonValue[j]);
				createElement1.setAttribute("is", "prime");
				String createElement1Text = objectJson.getJSONObject(i + pace).get(jsonValue[j]).toString();
				createElement1.appendChild(doc.createTextNode(createElement1Text));
				keElement.appendChild(createElement1);
			} else {
				Element createElement1 = doc.createElement(jsonValue[j]);
				String createElement1Text = objectJson.getJSONObject(i + pace).get(jsonValue[j]).toString();
				createElement1.appendChild(doc.createTextNode(createElement1Text));
				keElement.appendChild(createElement1);
			}

		}

		return keElement;

	}

	/**
	 * 返回所json对象的值
	 *
	 * @param jsonObj
	 * @return
	 */
	public String[] getKeyValuString(JSONObject jsonObj) {
		String[] arrValueString = new String[3];
		Iterator<String> keys = jsonObj.keys();
		for (int i = 0; i < jsonObj.length(); i++) {
			String next = keys.next();
			String jsonObjString = jsonObj.get(next).toString();
			arrValueString[i] = jsonObjString;
		}
		return arrValueString;

	}

	/**
	 * 创建dom
	 *
	 * @param jsonArray
	 * @param doc
	 * @param documentElement
	 */
	public void createDom(JSONArray jsonArray, Document doc, Element documentElement) {
		for (int i = 0; i < 9; i++) {
			Element keysElement = doc.createElement("keys");
			Element element = getElement(jsonArray, i, 0, doc);
			Element element1 = getElement(jsonArray, i, 9, doc);
			Element element2 = getElement(jsonArray, i, 18, doc);
			Element element3 = getElement(jsonArray, i, 27, doc);
			keysElement.appendChild(element);
			keysElement.appendChild(element1);
			keysElement.appendChild(element2);
			keysElement.appendChild(element3);
			documentElement.appendChild(keysElement);
		}
		for (int i = 0; i < 9; i++) {
			Element keysElement = doc.createElement("keys");
			Element element = getElement(jsonArray, i, 36, doc);
			Element element1 = getElement(jsonArray, i, 45, doc);
			Element element2 = getElement(jsonArray, i, 54, doc);
			Element element3 = getElement(jsonArray, i, 63, doc);
			keysElement.appendChild(element);
			keysElement.appendChild(element1);
			keysElement.appendChild(element2);
			keysElement.appendChild(element3);
			documentElement.appendChild(keysElement);
		}
	}

}
